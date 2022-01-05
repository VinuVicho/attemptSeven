package me.vinuvicho.attemptSeven.entity.comment;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
public class Comment {

    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private Long id;

    private String title;
    private String text;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> liked = null;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> disliked = null;
}
