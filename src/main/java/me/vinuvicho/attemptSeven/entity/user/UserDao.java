package me.vinuvicho.attemptSeven.entity.user;

import java.util.Optional;

public interface UserDao {
    public Optional<User> selectUserByUsername(String username);
}
