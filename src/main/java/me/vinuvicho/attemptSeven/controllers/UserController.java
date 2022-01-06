package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserDao;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/user"})
public class UserController {

    private final UserService userService;
    private final UserDao userDao;

    @GetMapping("/me")
    public String myAccount() {         //not sure if that works
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/user/" + ((UserDetails) principal).getUsername();
    }

    @GetMapping("/all")
    public String getAllUsers() {
        List<User> users = userDao.findAll();
        return users.toString();
    }

    @GetMapping("/{credentials}")
    public String findUserByUsername(@PathVariable String credentials) {
        User foundUser = userService.getUser(credentials);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            User user = userService.getUser(((UserDetails) principal).getUsername());
            if (foundUser == user) {
                return myAccount(user);
            }
        } catch (Exception ignored) { }

        return foundUser.toString();
    }

    public String myAccount(User user) {
        return "my account: " + user.toString();
    }
}
