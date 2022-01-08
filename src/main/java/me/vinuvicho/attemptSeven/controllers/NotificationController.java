package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.notification.Notification;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/notification"})
public class NotificationController {

    private UserService userService;

    @GetMapping("/all")
    public String getNotifications() {
        User user = getCurrentUser();
        if (user == null) {
            return "NOT LOGGED IN";
        }
        return "TODO";          //TODO: notification return
    }


    public User getCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(((UserDetails) principal).getUsername());
        } catch (Exception e) {
            return null;            //BAD TONE
        }
    }
}
