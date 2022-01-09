package me.vinuvicho.attemptSeven.entity.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
public class Notification {

    @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private User user;
    @ManyToOne
    @ToString.Exclude
    private User additionalUser;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    private boolean closed = false;                 //TODO: make delete, not closed
    private LocalDateTime time;

    public Notification(User user, User additionalUser, NotificationType notificationType, Integer number, LocalDateTime time) {
        this.user = user;
        this.additionalUser = additionalUser;
        this.notificationType = notificationType;
        this.number = number;
        this.time = time;
    }

    private Integer number;

    public Notification() {

    }
}
