package me.vinuvicho.attemptSeven.entity.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentDao commentDao;

    public Comment getComment(Long commentId) {
        //noinspection OptionalGetWithoutIsPresent
        return commentDao.findById(commentId).get();
    }

}
