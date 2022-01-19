package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.comment.Comment;
import me.vinuvicho.attemptSeven.entity.comment.CommentRequest;
import me.vinuvicho.attemptSeven.entity.comment.CommentService;
import me.vinuvicho.attemptSeven.entity.post.Post;
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

@Controller
@AllArgsConstructor
@RequestMapping(value = {"/post"})
public class PostController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;

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
        List<Post> loadedPosts = postService.getFullPosts(posts);
        model.addAttribute("posts", loadedPosts);
        return "pages/post/all-posts";
    }

    @GetMapping("/")
    public String mainPostPage() {          //TODO: not tested
        return "redirect:/";                //TODO: prob move that here and there make some real 'main page'
    }

    @PreAuthorize("hasAuthority('post:create')")
    @GetMapping("/new")
    public String createPost(Model model) {
        model.addAttribute("postRequest", new PostRequest());
        return "pages/post/new";
    }
    @PreAuthorize("hasAuthority('post:create')")
    @PostMapping("/new")
    public String postPost(@ModelAttribute PostRequest postRequest, Model model) {
        User user = userService.getFullCurrentUser();
        Post post = postService.savePost(user, postRequest);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        model.addAttribute("post", post);
        model.addAttribute("newComment", new CommentRequest());
        model.addAttribute("currentUser", user);
        model.addAttribute("comments", post.getComments());
        return "pages/post/one";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        postService.likePost(post, user);
        return "redirect:/post/" + postId;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HALF_ADMIN', 'ROLE_USER')")
    @GetMapping("/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        postService.dislikePost(post, user);
        return "redirect:/post/" + postId;
    }

    @PreAuthorize("hasAuthority('post:comment')")
    @PostMapping("/{postId}/comment")
    public String commentPost(@PathVariable Long postId, @ModelAttribute CommentRequest commentRequest) {
        User user = userService.getCurrentUser();
        postService.commentPost(user, postId, commentRequest);
        return "redirect:/post/" + postId;
    }

    @PreAuthorize("hasAuthority('post:comment')")
    @GetMapping("/{postId}/{commentId}/like")
    public String likeComment(@PathVariable Long postId, @PathVariable Long commentId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        Comment comment = commentService.getComment(commentId);
        postService.likeComment(user, post, comment);
        return "redirect:/post/" + postId;
    }
    @PreAuthorize("hasAuthority('post:comment')")
    @GetMapping("/{postId}/{commentId}/dislike")
    public String dislikeComment(@PathVariable Long postId, @PathVariable Long commentId) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        Comment comment = commentService.getComment(commentId);
        postService.dislikeComment(user, post, comment);
        return "redirect:/post/" + postId;
    }

    @GetMapping("/{postId}/{commentId}/disliked")
    public String dislikedComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        Comment comment = commentService.getComment(commentId);
        model.addAttribute("whatIsShown", "Users, that disliked " +
                "comment " + comment.getTitle() + " on a post " +
                "<a href=\"/post/" + post.getId() + "\">" + post.getTitle() + "</a>");
        model.addAttribute("users", comment.getDisliked());
        return "pages/user/all-users";
    }
    @GetMapping("/{postId}/{commentId}/liked")
    public String likedComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
        User user = userService.getCurrentUser();
        Post post = postService.getPost(user, postId);
        Comment comment = commentService.getComment(commentId);
        model.addAttribute("whatIsShown", "Users, that liked " +
                "comment " + comment.getTitle() + " on a post " +
                "<a href=\"/post/" + post.getId() + "\">" + post.getTitle() + "</a>");
        model.addAttribute("users", comment.getLiked());
        return "pages/user/all-users";
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
