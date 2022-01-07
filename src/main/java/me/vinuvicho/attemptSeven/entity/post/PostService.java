package me.vinuvicho.attemptSeven.entity.post;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostService {

    private PostDao postDao;

    public Post savePost(User postedBy, PostRequest postRequest) {

        Post post = new Post(postedBy, postRequest.getTitle(), postRequest.getText(),
                (Objects.equals(postRequest.getImage(), "")) ? null : postRequest.getImage(),
                LocalDateTime.now());
        postDao.save(post);
        return post;
    }

}
