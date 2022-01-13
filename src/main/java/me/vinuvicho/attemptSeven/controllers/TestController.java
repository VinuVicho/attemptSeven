package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenDao;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final UserService userService;
    private final ConfirmationTokenDao tokenDao;

    @DeleteMapping
    public String testDelete() {
        tokenDao.deleteById(1L);
        return "posts";
    }

    @GetMapping("/1")
    public String testObjects() {
        User user = userService.getUser("Kodlon");
        String username = user.getUsername();
        username = username + "11";
        System.out.println(user.getUsername());
        user.setUsername(username);
        System.out.println(userService.getUser(username).getUsername());
        return "nothing";
    }
}
