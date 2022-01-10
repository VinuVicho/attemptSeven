package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostComparator;
import me.vinuvicho.attemptSeven.entity.post.PostRequest;
import me.vinuvicho.attemptSeven.entity.post.PostService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/post"})
public class PostController {

    private UserService userService;
    private PostService postService;

    @GetMapping("/all")
    public String getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return posts.toString();
    }

    @GetMapping("/my")
    public String myPosts() {
        Set<Post> posts = getCurrentUser().getPosts();
        return posts.toString();
    }

    @GetMapping("/new")
    public String createPost() {
        return "creating post";
    }

    @GetMapping("/")
    public String mainPostPage() {
        User user = getCurrentUser();
        if (user == null || user.getSubscribedTo() == null) {
            return postService.getAllPosts().toString();
        }
        Set<Post> posts = new TreeSet<>(new PostComparator());
        for (User u: user.getSubscribedTo()) {
            posts.addAll(u.getPosts());
        }

        return posts.toString();
    }

//    @PreAuthorize("hasAuthority('post:create')")
    @PostMapping("/new")
    public String postPost(@RequestBody PostRequest postRequest) {
//        User user = getCurrentUser();
        User user = userService.getUser("1");
        Post post = postService.savePost(user, postRequest);
        return post.toString();
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId) {
        Post post = postService.checkPostAvailability(getCurrentUser(), postId);
        return post.toString();
    }
    @GetMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        User user = getCurrentUser();
        if (user == null) throw new IllegalStateException("not logged id");
        Post post = postService.checkPostAvailability(getCurrentUser(), postId);
        postService.likePost(post, user);
        return post.toString();
    }
    @GetMapping("/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId) {
        User user = getCurrentUser();
        if (user == null) throw new IllegalStateException("not logged id");
        Post post = postService.checkPostAvailability(getCurrentUser(), postId);
        postService.dislikePost(post, user);
        return post.toString();
    }

    public User getCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(((UserDetails) principal).getUsername());
        } catch (Exception e) {
            return null;
        }
    }
}
