package me.vinuvicho.attemptSeven.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {

    private final List<? extends GrantedAuthority> grantedAuthorities;
    private final String password;
    private final String username;
    private final String email;

    private final boolean expired;
    private final boolean locked;
    private final boolean expiredCredentials;
    private final boolean enabled;


    public User(List<? extends GrantedAuthority> grantedAuthorities,
                String password,
                String username,
                String email,
                boolean expired,
                boolean locked,
                boolean expiredCredentials,
                boolean enabled) {
        this.grantedAuthorities = grantedAuthorities;
        this.password = password;
        this.username = username;
        this.email = email;
        this.expired = expired;
        this.locked = locked;
        this.expiredCredentials = expiredCredentials;
        this.enabled = enabled;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expiredCredentials;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

