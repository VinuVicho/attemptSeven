package me.vinuvicho.attemptSeven.tests;

import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TestDao extends JpaRepository<TestEntity, Long> {
//public interface TestDao extends CrudRepository<TestEntity, Long> {
    @EntityGraph(attributePaths = "users")
    TestEntity getByName(String name);
}
