package me.vinuvicho.attemptSeven.entity.user;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.notification.NotificationService;
import me.vinuvicho.attemptSeven.entity.notification.NotificationType;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationToken;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenService;
import me.vinuvicho.attemptSeven.registration.token.TokenType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;
    private final NotificationService notificationService;

    public boolean hasAccessToPosts(User from, User to) {
        if (to.getProfileType() == ProfileType.PUBLIC || to.getProfileType() == ProfileType.PROTECTED) return true;
        if (from == null) return false;
        if (to.getBlockedUsers().contains(from)) return false;
        return to.getSubscribers().contains(from);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    public User getUser(String credentials) {
        try {
            Long id = Long.valueOf(credentials);
            @SuppressWarnings("UnnecessaryLocalVariable")                //не вибиває помилку якщо відразу ретирн
            User user = userDao.getById(id);
            return user;
        } catch (Exception e) {
            Optional<User> OUser =  userDao.findByUsername(credentials);
            if (OUser.isPresent()) return OUser.get();
            throw new IllegalStateException("No user found");
        }
    }

    public void addFriend(User mainUser, User toFollow) {
        Set<User> subscribedTo = new HashSet<>();
        Set<User> subscribers = new HashSet<>();

        if (mainUser.getSubscribedTo() != null) {       //removing if already
            subscribedTo = mainUser.getSubscribedTo();  //TODO: remove notification
            if (subscribedTo.contains(toFollow)) {
                subscribedTo.remove(toFollow);
                subscribers = toFollow.getSubscribers();
                subscribers.remove(mainUser);
                mainUser.setSubscribedTo(subscribedTo);         //prob to delete
                toFollow.setSubscribers(subscribers);           //prob to delete
                userDao.save(mainUser);
                userDao.save(toFollow);
                return;
            }
        }

        if ((mainUser.getBlockedUsers() != null && mainUser.getBlockedUsers().contains(toFollow)) ||        //check does it work
                toFollow.getBlockedUsers() != null && toFollow.getBlockedUsers().contains(mainUser)) {
            throw new IllegalStateException("User blocked");
        }
        if (toFollow.getProfileType() == ProfileType.ONLY_SUBSCRIBERS || toFollow.getProfileType() == ProfileType.FRIENDS) {
            notificationService.createNotification(toFollow, mainUser, NotificationType.WANTS_TO_BECOME_SUBSCRIBER, null);
        } else {
            UserServiceSimple.justAddFriend(mainUser, toFollow, subscribedTo, subscribers, userDao);
        }
    }
    public void blockUser(User mainUser, User toBlock) {
        Set<User> blockedUsers = new HashSet<>();
        if (mainUser.getBlockedUsers() != null) {
            blockedUsers = mainUser.getBlockedUsers();
        }
        if (blockedUsers.contains(toBlock)) {
            blockedUsers.remove(toBlock);
        } else {
            blockedUsers.add(toBlock);
            mainUser.getSubscribedTo().remove(toBlock);         //not sure if that works
            toBlock.getSubscribedTo().remove(mainUser);
            mainUser.getSubscribers().remove(toBlock);
            toBlock.getSubscribers().remove(mainUser);
        }
        mainUser.setBlockedUsers(blockedUsers);
        userDao.save(mainUser);
        userDao.save(toBlock);
    }

    public User getFullUser(String credentials) {
        try {
            Long id = Long.valueOf(credentials);
            System.out.println(id);
            @SuppressWarnings("UnnecessaryLocalVariable")                //не вибиває помилку якщо відразу ретирн
            User user = userDao.findByIdAndEnabled(id, true);
            return user;
        } catch (Exception e) {
            Optional<User> OUser =  userDao.findByUsernameAndEnabled(credentials, true);
            if (OUser.isPresent()) return OUser.get();
            throw new IllegalStateException("No user found");
        }
    }

    public User getCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = getUser(((UserDetails) principal).getUsername());
            user.setLastActivity(LocalDateTime.now());          //тут мала б збиратись статистика
            userDao.save(user);
            return user;
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }
    public User getFullCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = getFullUser(((UserDetails) principal).getUsername());
            user.setLastActivity(LocalDateTime.now());          //тут мала б збиратись статистика
            userDao.save(user);
            return user;
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }

    public void updateUser(User user, UserRequest request) {
        user.updateUser(request);
        userDao.save(user);
//        return user;          //not using for now
    }

    public void enableUser(String email) {
        userDao.enableUser(email);
    }
    @Transactional
    public ConfirmationToken signUpUser(User user) {       //returns token to confirm
        boolean userExists =                                //TODO (optimize): make this using 1 db transaction
                userDao.findByUsername(user.getUsername()).isPresent() ||
                userDao.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            User realUser =
                    userDao.findByEmail(user.getEmail()).orElse(
                    userDao.findByUsername(user.getUsername()).orElseThrow());
            if (realUser.isEnabled())
                throw new IllegalStateException("User with such email or username already exists");
            else {
                ConfirmationToken token = tokenService.getVerifyTokenByUser(realUser);
                if (token.getExpiresAt().minusMinutes(5).isAfter(LocalDateTime.now())) {
                    return token;
                }
                Long userId = token.getUser().getId();
                tokenService.deleteToken(token.getToken());
                userDao.deleteById(userId);
            }
        }
        //Steal password here   xd
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);

        ConfirmationToken token = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user,
                TokenType.VERIFY_ACCOUNT
        );
        tokenService.saveConfirmationToken(token);
        return token;
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
