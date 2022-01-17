package me.vinuvicho.attemptSeven.entity.post;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostDao  extends JpaRepository<Post, Long> {

    List<Post> getPostsByPostedBy(User postedBy);
}
