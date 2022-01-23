package me.vinuvicho.attemptSeven.entity.comment;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private LocalDateTime postedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User postedBy;

//    TODO: replies
//TODO: postedAt

        //TODO: prob make counters for liked/disliked to enable lazy initialization
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> liked = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> disliked = new HashSet<>();

    public Comment() {
    }

    public Comment(String title, String text, User postedBy) {
        this.title = title;
        this.text = text;
        this.postedBy = postedBy;
        this.postedAt = LocalDateTime.now();
    }
}
