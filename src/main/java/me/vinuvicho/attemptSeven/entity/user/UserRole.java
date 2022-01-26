package me.vinuvicho.attemptSeven.entity.user;

import com.google.common.collect.Sets;
import me.vinuvicho.attemptSeven.security.ApplicationUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static me.vinuvicho.attemptSeven.security.ApplicationUserPermission.*;

public enum UserRole {      //TODO: make normal roles
    BANNED(Sets.newHashSet()),          //Спрінг розуміє як ROLE_BANNED
    USER(Sets.newHashSet(POST_CREATE, POST_COMMENT, USER_ADD)),
    ADMIN(Sets.newHashSet(POST_EDIT, POST_COMMENT, POST_CREATE, USER_EDIT, USER_ADD)),
    CREATOR(Sets.newHashSet(POST_EDIT, POST_COMMENT, POST_CREATE, USER_EDIT, ADMIN_EDIT, USER_ADD));
    
    private final Set<ApplicationUserPermission> permissions;

    UserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
