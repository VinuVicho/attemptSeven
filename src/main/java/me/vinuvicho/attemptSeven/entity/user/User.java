package me.vinuvicho.attemptSeven.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User implements UserDetails {

    private Long id;
    private final String password;
    private final String username;
    private final String email;
    private final UserRole userRole;

    private final boolean locked;
    private final boolean enabled;

    public User(UserRole userRole,          //not sure
                String password,
                String username,
                String email,
                boolean locked,
                boolean enabled) {
        this.userRole = userRole;
        this.password = password;
        this.username = username;
        this.email = email;
        this.locked = locked;
        this.enabled = enabled;
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
}

