package me.vinuvicho.attemptSeven.entity.comment;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CommentRequest {
    private String title;
    private String text;
}
