package me.vinuvicho.attemptSeven.entity.post;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.ProfileType;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private PostDao postDao;

    public List<Post> getUserSubscribedToPosts(User user) {
        List<Post> userPosts = new ArrayList<>();
        if (user != null) {
            Set<User> subscribedTo = user.getSubscribedTo();
            for (User s : subscribedTo) {
                userPosts.addAll(postDao.getPostsByPostedBy(s));
            }
            if (!userPosts.isEmpty()) {
                userPosts.sort(new PostComparator());               //not sure if it sorts
                return userPosts;
            }
        }
        return getAllPosts(user);           //якщо ні на кого не підписаний/вони не мають постів
    }

    public Post getPost(User user, Long id) {
        Post post = postDao.getById(id);
        if (post.getPostedBy().getProfileType() == ProfileType.PUBLIC
                || post.getPostedBy().getProfileType() == ProfileType.PROTECTED)
            return post;
        if (user != null && post.getPostedBy().getBlockedUsers().contains(user)) {
            if (user.getSubscribedTo().contains(post.getPostedBy())) return post;
        }
        throw new IllegalStateException("No access");
    }

    public boolean hasAccessToPost(User user, Post post) {
        if (post.getPostedBy().getProfileType() == ProfileType.PUBLIC
                || post.getPostedBy().getProfileType() == ProfileType.PROTECTED)
            return true;
        if (user != null && post.getPostedBy().getBlockedUsers().contains(user)) {
            return user.getSubscribedTo().contains(post.getPostedBy());
        }
        return false;
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
        post.setLiked(likedBy);
        post.setDisliked(dislikedBy);
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
        post.setLiked(likedBy);
        post.setDisliked(dislikedBy);
        postDao.save(post);
    }

    public List<Post> getAllPosts(User user) {
        List<Post> posts = postDao.findAll();
        return posts.stream()
                .filter(post -> hasAccessToPost(user, post))
                .collect(Collectors.toList());
    }

    public Post savePost(User postedBy, PostRequest postRequest) {
        Post post = new Post(
                postedBy,
                postRequest.getTitle(),
                postRequest.getText(),
                (Objects.equals(postRequest.getImage(), "")) ? null : postRequest.getImage(),       //TODO: make перевірку на лінк
                LocalDateTime.now()
        );
        postDao.save(post);
        return post;
    }

}
