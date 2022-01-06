package me.vinuvicho.attemptSeven.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.vinuvicho.attemptSeven.entity.post.Post;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")                              //user -- ключове слово в Postgre
public class User implements UserDetails {

    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String username;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private ProfileType profileType;                //TODO: profile types

    @Enumerated(EnumType.STRING)
    private Language language = Language.UA;        //TODO: languages

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Post> post = null;
            //TODO: make another class for not-main info?
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> subscribedTo = null;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> blockedUsers = null;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Post> saved = null;                 //TODO

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> subscribers = null;

    private String profilePhoto = null;             //TODO: prof pic and 'about'
    private String about = null;
    private LocalDateTime createdAt = null;         //TODO: time when created
    private LocalDateTime lastActivity = null;      //TODO: time when acted last time

    @ToString.Exclude
    private boolean locked = false;
    @ToString.Exclude
    private boolean enabled = false;

    public User(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    public User() {}
}

