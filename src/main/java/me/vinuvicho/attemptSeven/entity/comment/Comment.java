package me.vinuvicho.attemptSeven.entity.comment;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
public class Comment {

    @Id
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
