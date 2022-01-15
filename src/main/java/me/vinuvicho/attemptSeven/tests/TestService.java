package me.vinuvicho.attemptSeven.tests;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TestService {

    private TestDao testDao;

    public TestEntity getTestEntity(String name) {
        TestEntity test = testDao.getByName(name);
        List<User> users = test.getUsers();
        System.out.println(users);            //WHY
        return test;
    }

    public void save(TestEntity test) {
        testDao.save(test);
    }

    public Set<User> getUsers(Set<User> test) {
        return null;
    }
}
