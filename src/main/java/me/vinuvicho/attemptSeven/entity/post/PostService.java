package me.vinuvicho.attemptSeven.entity.post;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.ProfileType;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class PostService {

    private PostDao postDao;

    public Post checkPostAvailability(User user, Long id) {
        Post post = postDao.getById(id);
        if (post.getPostedBy().getProfileType() == ProfileType.PUBLIC
                || post.getPostedBy().getProfileType() == ProfileType.PROTECTED)
            return post;
        if (user != null && post.getPostedBy().getBlockedUsers().contains(user)) {
            if (user.getSubscribedTo().contains(post.getPostedBy())) return post;
        }
        throw new IllegalStateException("No access");
    }

    public void likePost(Post post, User user) {
        Set<User> likedBy = post.getLiked();
        Set<User> dislikedBy = post.getDisliked();
        if (likedBy.contains(user)) {
            likedBy.remove(user);
        } else {
            likedBy.add(user);
            dislikedBy.remove(user);
        }
        post.setLiked(likedBy);                     //TODO: check if really need this
        post.setDisliked(dislikedBy);               //TODO: check if really need this
        postDao.save(post);
    }

    public void dislikePost(Post post, User user) {
        Set<User> likedBy = post.getLiked();
        Set<User> dislikedBy = post.getDisliked();
        if (dislikedBy.contains(user)) {
            dislikedBy.remove(user);
        } else {
            dislikedBy.add(user);
            likedBy.remove(user);
        }
        post.setLiked(likedBy);                     //TODO: check if really need this
        post.setDisliked(dislikedBy);               //TODO: check if really need this
        postDao.save(post);
    }

    public Post savePost(User postedBy, PostRequest postRequest) {
        Post post = new Post(
                postedBy,
                postRequest.getTitle(),
                postRequest.getText(),
                (Objects.equals(postRequest.getImage(), "")) ? null : postRequest.getImage(),
                LocalDateTime.now()
        );
        postDao.save(post);
        return post;
    }

}
