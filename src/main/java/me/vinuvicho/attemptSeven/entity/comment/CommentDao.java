package me.vinuvicho.attemptSeven.entity.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CommentDao extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long id);
}
