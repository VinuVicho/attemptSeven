package me.vinuvicho.attemptSeven.entity.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static me.vinuvicho.attemptSeven.entity.notification.NotificationType.*;

@Getter
@Setter
public class NotificationHtml {
    private String title;
    private String text;
    private String id;

    public NotificationHtml(Notification n) {
        String username = n.getAdditionalUser().getUsername();
        id = String.valueOf(n.getId());
        if (n.getNotificationType() == NEW_SUBSCRIBER) {
            title = username + " subscribed";
            text = "<a href=\"/user/" + username + "\">" + username + "</a> subscribed to you "
                    + "<small>" + getTimeToNow(n.getTime()) + "</small>";
            return;
        }
        if (n.getNotificationType() == WANTS_TO_BECOME_SUBSCRIBER) {
            title = "<a href=\"/user/" + username + "\">" + username + "</a> wants to become subscriber";
            String baseHref = "<a href=\"/notification/" + id;
            text = baseHref + "/apply\"> Apply </a>" +
                    baseHref + "/reject\"> Reject </a>" +
                    baseHref + "/block\"> Block </a>";
            return;
        }
        if (n.getNotificationType() == NEW_MESSAGE) {
            title = username + " messaged you";
            text = "<a href=\"/user/" + username + "\">" + username + "</a> " +     //TODO: make link to chat
                    "sent " + n.getNumber() + " messages to you " +
                    "<small>" + getTimeToNow(n.getTime()) + "</small>";
        }
    }

    public NotificationHtml(List<Notification> notificationList, NotificationType type) {      //TODO
        StringBuilder buildId = new StringBuilder();                                            //check if works normally
        for (Notification n: notificationList) {
            buildId.append(n.getId()).append("-");
        }
        buildId.deleteCharAt(buildId.length());
        id = buildId.toString();

        if (type == NEW_SUBSCRIBER) {
            title = "New subscribers";
            text = "<a href=\"/notification/subscribed/" + id + "\">" +
                    notificationList.size() + " new subscribers</a>";
            return;
        }
        title = "New Messages";                             //check does it work correctly
        text = "<a href=\"/chat/\"> You have " + notificationList.stream().mapToInt(Notification::getNumber).sum() + "messages" +
                notificationList.size() + " chats</a>";
    }

    private String getTimeToNow(LocalDateTime time) {       //TODO: make normal time creation
        int difference = (int) ChronoUnit.MINUTES.between(time, LocalDateTime.now());
        return (difference + "min");
    }
}
