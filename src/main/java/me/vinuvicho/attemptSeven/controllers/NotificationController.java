package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.notification.NotificationService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/notification"})
public class NotificationController {

    private UserService userService;
    private NotificationService notificationService;

    @GetMapping("/all")
    public String getNotifications() {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "NOT LOGGED IN";
        }
        return notificationService.getUserNotificationHtml(user).toString();
    }

    @GetMapping("/{id}/reject")
    public String rejectNotification(@PathVariable String id) {
        User user = userService.getCurrentUser();
        notificationService.rejectNotification(user, Long.valueOf(id));
        return "rejected?";
    }
    @GetMapping("/{id}/block")
    public String blockUserNotification(@PathVariable String id) {
        User user = userService.getCurrentUser();
        notificationService.blockUserNotification(user, Long.valueOf(id));
        return "blocked?";
    }
    @GetMapping("/{id}/apply")
    public String applyUserNotification(@PathVariable String id) {
        User user = userService.getCurrentUser();
        notificationService.applyUserNotification(user, Long.valueOf(id));
        return "applied?";
    }

    @GetMapping("/subscribed")
    public String subscribedUsers() {
        User user = userService.getCurrentUser();
        return notificationService.getUserNewSubscribers(user).toString();
    }
}
