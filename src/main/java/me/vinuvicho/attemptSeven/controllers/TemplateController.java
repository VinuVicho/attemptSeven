package me.vinuvicho.attemptSeven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }
            //just something
//    @GetMapping("posts")
//    public String getPosts() {
//        return "posts";
//    }
}
