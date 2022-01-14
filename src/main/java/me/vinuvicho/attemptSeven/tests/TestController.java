package me.vinuvicho.attemptSeven.tests;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenDao;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final UserService userService;
    private final TestService testService;
    private final ConfirmationTokenDao tokenDao;


    @DeleteMapping
    public String testDelete() {
        tokenDao.deleteById(1L);
        return "posts";
    }

    @GetMapping("/2")
    public String testOneToMany() {
        List<User> test = testService.getTestEntity("testName").getUsers();
        return test.toString();
    }
    @GetMapping("/a2")
    public void beforeTestOneToMany() {
        List<User> users = new ArrayList<>();
        users.add(userService.getUser("Kodlon"));
        TestEntity test = new TestEntity("testName", users);
        testService.save(test);
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
