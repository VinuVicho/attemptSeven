package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostService;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/login")
    public String login() {
        return "pages/basic/login";
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        User currentUser = userService.getFullCurrentUser();
        List<Post> userPosts = postService.getUserSubscribedToPosts(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("posts", userPosts);
        return "pages/post/all-posts";
    }

}
