package me.vinuvicho.attemptSeven.tests;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.notification.Notification;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity

@Table(name = "test")
@NamedEntityGraph()
public class TestEntity {
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> users = null;

    public TestEntity() {
    }

    public TestEntity(Set<User> users) {
        this.users = users;
    }
}
