package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostComparator;
import me.vinuvicho.attemptSeven.entity.post.PostRequest;
import me.vinuvicho.attemptSeven.entity.post.PostService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.boot.Banner;
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
        model.addAttribute("posts", posts);
        return "pages/post/all-posts";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    @GetMapping("/my")
    public String myPosts(Model model) {
        Set<Post> posts = userService.getFullCurrentUser().getPosts();
        model.addAttribute("posts", posts);
        return "pages/post/all-posts";
    }

    @PreAuthorize("hasAuthority('post:create')")
    @GetMapping("/new")
    public String createPost(Model model) {
        model.addAttribute("postRequest", new PostRequest());
        return "pages/post/new";
    }

    @GetMapping("/")
    public String mainPostPage() {
        return "redirect:/";                //TODO: prob move that here and there make some real 'main page'
    }

    @PreAuthorize("hasAuthority('post:create')")
    @PostMapping("/new")
    public String postPost(@ModelAttribute PostRequest postRequest, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.savePost(user, postRequest);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        model.addAttribute("post", post);
        model.addAttribute("currentUser", user);
        return "pages/post/one";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {                     //TODO: check if user has access to post
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        postService.likePost(post, user);
        return "redirect:/post/" + postId;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId) {                  //TODO: check if user has access to post
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        postService.dislikePost(post, user);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/{postId}/disliked")
    public String disliked(@PathVariable Long postId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        model.addAttribute("whatIsShown", "Users, that disliked " +
                "<a href=\"/post/" + post.getId() + "\">" + post.getTitle() + "</a>");
        model.addAttribute("users", post.getDisliked());
        return "pages/user/all-users";
    }

    @GetMapping("/{postId}/liked")
    public String liked(@PathVariable Long postId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        model.addAttribute("whatIsShown", "Users, that liked " +
                "<a href=\"/post/" + post.getId() + "\">" + post.getTitle() + "</a>");
        model.addAttribute("users", post.getLiked());
        return "pages/user/all-users";
    }
}
