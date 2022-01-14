package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserRequest;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "pages/user/all-users";
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

    @GetMapping("/{credentials}/subscribers")
    @ResponseBody           //TODO: make page for this
    public String getSubscribers(@PathVariable String credentials, Model model) {
        User user = userService.getUser(credentials);
        return user.getSubscribers().toString();
    }

    @GetMapping("/{credentials}/subscribed")
    @ResponseBody           //TODO: make page for this
    public String getSubscribedTo(@PathVariable String credentials, Model model) {
        User user = userService.getUser(credentials);
        return user.getSubscribedTo().toString();
    }

    @GetMapping("/{credentials}")
    public String findUserByUsername(@PathVariable String credentials, Model model) {
        User foundUser = userService.getFullUser(credentials);
        User thisUser = getCurrentUser();
        if (thisUser != null && foundUser.getId().equals(thisUser.getId())) {
            return myAccount(foundUser);
        }
        if (!userService.hasAccessToPosts(thisUser, foundUser)) {
            throw new IllegalStateException("User hidden");
        }
        UserRequest userRequest = new UserRequest(foundUser);
        System.out.println(userRequest);
        model.addAttribute("foundUser", new UserRequest(foundUser));
        return "pages/user/profile";

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
