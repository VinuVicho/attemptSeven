package me.vinuvicho.attemptSeven.entity.user;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationToken;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenService;
import me.vinuvicho.attemptSeven.registration.token.TokenType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public String signUpUser(User user) {       //returns token to confirm
        boolean userExists =
                userDao.findByUsername(user.getUsername()).isPresent() ||
                userDao.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            //TODO if user exists but not confirmed, send confirmation email again
            User realUser =
                    userDao.findByUsername(user.getUsername()).orElse(
                    userDao.findByEmail(user.getEmail()).orElseThrow());
            if (realUser.isEnabled())
                throw new IllegalStateException("User with such email or username already exists");
            else {

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
        confirmationTokenService.saveConfirmationToken(token);
        return token.getToken();
    }

    public void enableUser(String email) {
        userDao.enableUser(email);
    }
}
