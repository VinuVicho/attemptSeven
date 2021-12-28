package me.vinuvicho.attemptSeven.entity.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public String signUpUser(User user) {
        boolean userExists =
                userDao.findByUsername(user.getUsername()).isPresent() ||
                userDao.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Username or Email is already taken");
        }
            //TODO зупинився тут

        return null;
    }
}
