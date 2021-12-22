package me.vinuvicho.attemptSeven.security;

public enum ApplicationUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),

    POST_CREATE("post:create"),
    POST_COMMENT("post:comment"),
    POST_EDIT("post:edit"),
    USER_EDIT("user:edit");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
