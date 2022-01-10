package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/me")
    public String myAccount() {         //not sure if that works
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/user/" + ((UserDetails) principal).getUsername();
    }

    @GetMapping("/all")
    public String getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.toString();
    }

    @PreAuthorize("hasAuthority('user:add')")
    @GetMapping("/{credentials}/to-friends")
    public String addFriend(@PathVariable String credentials) {
        User currentUser = getCurrentUser();
        User userToAdd = userService.getUser(credentials);
        if (!currentUser.equals(userToAdd)) {
            userService.addFriend(currentUser, userToAdd);
        } else
            throw new IllegalStateException("Cannot add yourself");
        return "redirect:/user/" + credentials;
    }

    @PreAuthorize("hasAuthority('user:add')")
    @GetMapping("/{credentials}/block")
    public String blockUser(@PathVariable String credentials) {
        User currentUser = getCurrentUser();
        User userToBlock = userService.getUser(credentials);
        if (!currentUser.equals(userToBlock)) {
            userService.blockUser(currentUser, userToBlock);
        } else
            throw new IllegalStateException("Cannot block yourself");
        return "redirect:/user/" + credentials;
    }

    @GetMapping("/{credentials}")
    public String findUserByUsername(@PathVariable String credentials) {
        User foundUser = userService.getUser(credentials);
        User thisUser = getCurrentUser();
        if (foundUser.equals(thisUser)) {
            return myAccount(thisUser);
        }
        if (!userService.hasAccessToPosts(thisUser, foundUser)) {
            throw new IllegalStateException("User hidden");
        }
        return foundUser.toString();

    }

    public String myAccount(User user) {
        return "my account: " + user.toString();
    }

    public User getCurrentUser() {              //TODO: move to UserService
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(((UserDetails) principal).getUsername());
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }
}
