package me.vinuvicho.attemptSeven.controllers;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "VinuVicho"),
            new User(2, "Kodlon"),
            new User(3, "Vouzze")
    );

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return USERS.stream()
                .filter(user -> userId.equals(user.getUserId()))
                .findFirst().orElseThrow(() -> new IllegalStateException("no user with " + userId + " id"));
    }
}
