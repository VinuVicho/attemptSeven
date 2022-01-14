package me.vinuvicho.attemptSeven.tests;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class TestService {

    private TestDao testDao;

    public TestEntity getTestEntity(Long id) {
        TestEntity test = testDao.getById(id);
        System.out.println(test.getUsers());            //WHY
        return test;
    }

    public void save(TestEntity test) {
        testDao.save(test);
    }

    public Set<User> getUsers(Set<User> test) {
        return null;
    }
}
