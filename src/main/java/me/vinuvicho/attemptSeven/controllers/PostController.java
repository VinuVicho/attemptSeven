package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.post.Post;
import me.vinuvicho.attemptSeven.entity.post.PostDao;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/post"})
public class PostController {

    private PostDao postDao;

    @GetMapping("/all")
    public String getAllPosts() {
        List<Post> posts = postDao.findAll();
        return posts.toString();
    }
}
