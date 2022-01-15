package me.vinuvicho.attemptSeven.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.post.Post;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserRequest {              //TODO: prob to remove
    private Long id;
    private String email;
    private String about;
    private String username;
    private String profilePhoto;
    private Set<Post> posts;
    private Set<User> subscribers;
    private Set<User> subscribedTo;
    private Set<User> blockedUsers;
    private ProfileType profileType;
    private LocalDateTime createdAt;            //TODO: not in html
    private LocalDateTime lastActivity;         //TODO: not in html

    public UserRequest(Long id, String username, ProfileType profileType, Set<Post> posts, Set<User> subscribedTo,
                       Set<User> blockedUsers, Set<User> subscribers, String profilePhoto, String about,
                       LocalDateTime createdAt, LocalDateTime lastActivity) {
        this.id = id;
        this.username = username;
        this.profileType = profileType;
        this.posts = posts;
        this.subscribedTo = subscribedTo;
        this.blockedUsers = blockedUsers;
        this.subscribers = subscribers;
        this.profilePhoto = profilePhoto;
        this.about = about;
        this.createdAt = createdAt;
        this.lastActivity = lastActivity;
    }

    public UserRequest(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileType = user.getProfileType();
        this.posts = user.getPosts();
        this.subscribers = user.getSubscribers();
        this.subscribedTo = user.getSubscribedTo();
        this.blockedUsers = user.getBlockedUsers();
        this.profilePhoto = user.getProfilePhoto();
        this.about = user.getAbout();
        this.createdAt = user.getCreatedAt();
        this.lastActivity = user.getLastActivity();

        if (subscribedTo == null) subscribedTo = new HashSet<>();
        if (blockedUsers == null) blockedUsers = new HashSet<>();
        if (subscribers == null) subscribers = new HashSet<>();
        if (posts == null) posts = new HashSet<>();
    }
}
