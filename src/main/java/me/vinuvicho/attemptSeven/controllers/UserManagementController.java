package me.vinuvicho.attemptSeven.controllers;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.web.bind.annotation.*;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/user")
public class UserManagementController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "VinuVicho"),
            new User(2, "Kodlon"),
            new User(3, "Vouzze")
    );

    @GetMapping
    public List<User> getAllUsers() {
        return USERS;
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        System.out.println(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        System.out.println(userId + " -- " + user);
    }
}
