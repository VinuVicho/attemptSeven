package me.vinuvicho.attemptSeven.entity.post;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostDao  extends JpaRepository<Post, Long> {

//    TODO: use https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.sorting

    @EntityGraph(attributePaths = {"comments.liked", "comments.disliked", "liked", "disliked", "postedBy"})
    Post getPostById(Long id);

    @EntityGraph(attributePaths = {"comments.liked", "comments.disliked", "liked", "disliked", "postedBy"})
    List<Post> getPostsByPostedBy(User postedBy);

    @EntityGraph(attributePaths = {"comments.liked", "comments.disliked", "liked", "disliked", "postedBy"})
    @Override
    List<Post> findAll();
}
