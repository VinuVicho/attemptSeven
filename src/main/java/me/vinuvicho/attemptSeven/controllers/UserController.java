package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserRequest;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import me.vinuvicho.attemptSeven.registration.RegistrationRequest;
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
        User currentUser = getFullCurrentUser();
        User userToAdd = userService.getFullUser(credentials);
        if (!currentUser.equals(userToAdd)) {
            userService.addFriend(currentUser, userToAdd);
        } else
            throw new IllegalStateException("Cannot add yourself");
        return "redirect:/user/" + credentials;
    }

    @PreAuthorize("hasAuthority('user:add')")
    @GetMapping("/{credentials}/block")
    public String blockUser(@PathVariable String credentials) {
        System.out.println("blocking");
        User currentUser = getFullCurrentUser();
        User userToBlock = userService.getFullUser(credentials);
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
    public String findUser(@PathVariable String credentials, Model model) {
        User foundUser = userService.getFullUser(credentials);
        User thisUser = getFullCurrentUser();
        if (thisUser != null) {
            model.addAttribute("currentUser", thisUser);
            if (foundUser.getId().equals(thisUser.getId())) {
                return "pages/user/my-profile";
            }
        }
//        else model.addAttribute("currentUser", null);                     //по дефолту нулл
        model.addAttribute("foundUser", foundUser);
        if (!userService.hasAccessToPosts(thisUser, foundUser)) {
            return "pages/user/hidden-profile";
        }
        return "pages/user/profile";
    }

    @GetMapping("/edit")                            //TODO: make admins able to change another user data
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    public String getEditPage(Model model) {
        User user = getCurrentUser();
        UserRequest userRequest = new UserRequest(user);
        model.addAttribute("userRequest", userRequest);
        return "pages/user/edit-profile";
    }


    @PostMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    public String updateUserProfile(@ModelAttribute UserRequest request) {
        User user = getFullCurrentUser();
        userService.updateUser(user, request);
        return "redirect:/user/" + user.getUsername();
    }


    public User getCurrentUser() {              //TODO: move to UserService
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(((UserDetails) principal).getUsername());
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }
    public User getFullCurrentUser() {              //TODO: move to UserService
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getFullUser(((UserDetails) principal).getUsername());
//            userService.userAction(user);
            return user;
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }
}
