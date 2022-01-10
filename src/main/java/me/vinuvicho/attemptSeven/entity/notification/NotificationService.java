package me.vinuvicho.attemptSeven.entity.notification;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserDao;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationDao notificationDao;
    private UserDao userDao;
    private UserService userService;

    public void blockUserNotification(User user, Long id) {
        Notification notification = notificationDao.getById(id);
        user.getNotifications().remove(notification);
        notification.setClosed(true);
        userService.onlyBlockUser(user, notification.getAdditionalUser());
        notificationDao.save(notification);
        userDao.save(user);
    }

    public void rejectNotification(User user, Long id) {
        Notification notification = notificationDao.getById(id);
        user.getNotifications().remove(notification);
        notification.setClosed(true);
        notificationDao.save(notification);
        userDao.save(user);
    }

    public void createNotification(User receiver, User additionalUser, NotificationType type, Integer number) {
        Notification notification = new Notification(receiver, additionalUser, type, number, LocalDateTime.now());
        Set<Notification> receiverNotifications = receiver.getNotifications();
        receiverNotifications.add(notification);
        receiver.setNotifications(receiverNotifications);
        notificationDao.save(notification);
        userDao.save(receiver);
    }

    public ArrayList<NotificationHtml> getUserNewSubscribers(User user) {
        ArrayList<NotificationHtml> result = new ArrayList<>();
        ArrayList<Notification> newSubscribers = (ArrayList<Notification>) user.getNotifications().stream()
                .filter((n) -> n.getNotificationType() == NotificationType.NEW_SUBSCRIBER)
                .collect(Collectors.toList());
        for (Notification n: newSubscribers) {
            result.add(new NotificationHtml(n));
        }
        return result;
    }

    public List<NotificationHtml> getUserNotificationHtml(User user) {
        Set<Notification> usersNotifications = user.getNotifications();
        ArrayList<NotificationHtml> result = new ArrayList<>();

        ArrayList<Notification> newMessages = (ArrayList<Notification>) usersNotifications.stream()
                .filter((n) -> n.getNotificationType() == NotificationType.NEW_MESSAGE)
                .collect(Collectors.toList());
        if (user.isCollapseMessages()) {
            if (newMessages.size() > 1) {
                result.add(new NotificationHtml(newMessages, NotificationType.NEW_MESSAGE));
            } else if (newMessages.size() == 1) {
                result.add(new NotificationHtml(newMessages.get(0)));
            }
        } else {
            for (Notification n: newMessages) {
                result.add(new NotificationHtml(n));
            }
        }

        ArrayList<Notification> newSubscribers = (ArrayList<Notification>) usersNotifications.stream()
                .filter((n) -> n.getNotificationType() == NotificationType.NEW_SUBSCRIBER)
                .collect(Collectors.toList());
        if (newSubscribers.size() > 1) {
            result.add(new NotificationHtml(newSubscribers, NotificationType.NEW_SUBSCRIBER));
        } else if (newSubscribers.size() == 1) {
            result.add(new NotificationHtml(newSubscribers.get(0)));
        }

        ArrayList<Notification> wantsToBecomeSub = (ArrayList<Notification>) usersNotifications.stream()
                .filter((n) -> n.getNotificationType() == NotificationType.WANTS_TO_BECOME_SUBSCRIBER)
                .collect(Collectors.toList());
        for (Notification n: wantsToBecomeSub) {
            result.add(new NotificationHtml(n));
        }

        return result;
    }

    public void applyUserNotification(User user, Long id) {
        Notification notification = notificationDao.getById(id);
        user.getNotifications().remove(notification);
        userService.onlyAddUser(user, notification.getAdditionalUser());
        notification.setClosed(true);
        notificationDao.save(notification);
        userDao.save(user);
    }
}
