package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostComparator;
import me.vinuvicho.attemptSeven.entity.post.PostRequest;
import me.vinuvicho.attemptSeven.entity.post.PostService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@AllArgsConstructor
@RequestMapping(value = {"/post"})
public class PostController {

    private UserService userService;
    private PostService postService;

    @GetMapping("/all")
    public String getAllPosts(Model model) {
        User user = userService.getCurrentUser();
        List<Post> posts = postService.getAllPosts(user);
        return posts.toString();
    }

    @GetMapping("/my")
    public String myPosts() {
        Set<Post> posts = userService.getCurrentUser().getPosts();
        return posts.toString();
    }

    @GetMapping("/new")
    public String createPost(Model model) {
        model.addAttribute("postRequest", new PostRequest());
        return "pages/post/new";
    }

    @GetMapping("/")
    public String mainPostPage() {
        User user = userService.getCurrentUser();
        if (user == null || user.getSubscribedTo() == null) {           //TODO: make by default and index page replace by subscribedTo posts
            return postService.getAllPosts(user).toString();
        }
        Set<Post> posts = new TreeSet<>(new PostComparator());
        for (User u: user.getSubscribedTo()) {
            posts.addAll(u.getPosts());
        }

        return posts.toString();
    }

    @PreAuthorize("hasAuthority('post:create')")
    @PostMapping("/new")
    public String postPost(@RequestBody PostRequest postRequest, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.savePost(user, postRequest);
        return post.toString();
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId) {
        Post post = postService.getPost(userService.getCurrentUser(), postId);
        return post.toString();
    }
    @GetMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalStateException("not logged id");
        Post post = postService.getPost(user, postId);
        postService.likePost(post, user);
        return post.toString();
    }
    @GetMapping("/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        if (user == null) throw new IllegalStateException("not logged id");
        Post post = postService.getPost(user, postId);
        postService.dislikePost(post, user);
        return post.toString();
    }

    @GetMapping("/{postId}/disliked")
    public String disliked(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        return post.getDisliked().toString();
    }

    @GetMapping("/{postId}/liked")
    public String liked(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        return post.getLiked().toString();
    }
}
