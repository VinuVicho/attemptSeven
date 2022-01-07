package me.vinuvicho.attemptSeven.entity.notification;


import me.vinuvicho.attemptSeven.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface NotificationDao extends JpaRepository<Notification, Long> {
}
