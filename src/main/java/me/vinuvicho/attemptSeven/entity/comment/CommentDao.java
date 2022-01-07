package me.vinuvicho.attemptSeven.entity.comment;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<User, Long> {

}
