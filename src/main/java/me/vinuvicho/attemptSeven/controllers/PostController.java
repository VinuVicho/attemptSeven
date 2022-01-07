package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostDao;
import me.vinuvicho.attemptSeven.entity.post.PostRequest;
import me.vinuvicho.attemptSeven.entity.post.PostService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/post"})
public class PostController {

    private PostDao postDao;
    private UserService userService;
    private PostService postService;

    @GetMapping("/all")
    public String getAllPosts() {
        List<Post> posts = postDao.findAll();
        return posts.toString();
    }

    @GetMapping("/")
    public String createPost() {
        return "creating post";
    }

//    @PreAuthorize("hasAuthority('post:create')")
    @PostMapping("/new")
    public String postPost(@RequestBody PostRequest postRequest) {
//        User user = getCurrentUser();
        User user = userService.getUser("1");
        Post post = postService.savePost(user, postRequest);
        return post.toString();
    }

    public User getCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUser(((UserDetails) principal).getUsername());
        } catch (Exception e) {
            throw new IllegalStateException("Not logged in");
        }
    }
}
