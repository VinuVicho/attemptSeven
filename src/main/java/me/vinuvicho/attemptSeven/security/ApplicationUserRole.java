package me.vinuvicho.attemptSeven.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static me.vinuvicho.attemptSeven.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    GUEST(Sets.newHashSet()),
    BANNED(Sets.newHashSet()),
    USER(Sets.newHashSet(POST_CREATE, POST_COMMENT)),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, USER_READ, USER_WRITE,
            POST_EDIT, POST_COMMENT, POST_CREATE, USER_EDIT)),
    HALF_ADMIN(Sets.newHashSet(COURSE_READ, USER_READ));


    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
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
