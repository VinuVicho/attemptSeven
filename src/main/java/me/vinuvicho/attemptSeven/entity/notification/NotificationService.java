package me.vinuvicho.attemptSeven.entity.notification;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationDao notificationDao;

    public Notification createNotification(User receiver, User additionalUser, NotificationType type, Integer number) {
        Notification notification = new Notification(receiver, additionalUser, type, number, LocalDateTime.now());
        Set<Notification> receiverNotifications = receiver.getNotifications();
        receiverNotifications.add(notification);
        receiver.setNotifications(receiverNotifications);
        notificationDao.save(notification);
        return notification;
    }
}
