package me.vinuvicho.attemptSeven.entity.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE WHERE a.email = ?1")
    void enableUser(String email);
}
