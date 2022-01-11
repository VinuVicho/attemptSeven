package me.vinuvicho.attemptSeven.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @DeleteMapping
    public String testDelete() {
        return "posts";
    }

}
