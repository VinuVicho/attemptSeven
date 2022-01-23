package me.vinuvicho.attemptSeven.entity.user;

import me.vinuvicho.attemptSeven.entity.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"posts", "subscribedTo", "blockedUsers", "subscribers"})
    Optional<User> findByUsernameAndEnabled(String username, boolean enabled);          //fully load
    @EntityGraph(attributePaths = {"posts", "subscribedTo", "blockedUsers", "subscribers"})
    User findByIdAndEnabled(Long id, boolean enabled);                                  //fully load

//    @Query("SELECT User.subscribedTo from User WHERE id = ?1")
//    List<User> getSubscribedToOnly(Long id);            //TODO: замутити якось, щоб витягувались тільки підписані

    void deleteById(Long id);

    @Query("select u from User u where u.username = :username or u.email = :email")                 //haven't tested yet
    User findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE WHERE a.email = ?1")
    void enableUser(String email);
}
