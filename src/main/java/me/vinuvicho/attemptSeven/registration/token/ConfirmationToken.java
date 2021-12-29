package me.vinuvicho.attemptSeven.registration.token;

import lombok.Getter;
import lombok.Setter;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ConfirmationToken {
    @SequenceGenerator(name = "confirm_token_sequence", sequenceName = "confirm_token_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirm_token_sequence")
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmed;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public ConfirmationToken() {
    }
}
