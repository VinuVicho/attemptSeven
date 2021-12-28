package me.vinuvicho.attemptSeven.registration;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public String register(RegistrationRequest request) {
        if (!new Validate().validateEmail(request.getEmail())) {
            throw new IllegalStateException("Email not valid");
        }
        return userService.signUpUser(
            new User(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword()
            )
        );
    }
}
