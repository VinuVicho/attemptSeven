package me.vinuvicho.attemptSeven.entity.post;

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
public class Post {

    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User postedBy;

    private String title;
    private String text;
    private String image = null;


    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> liked = null;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> disliked = null;
}