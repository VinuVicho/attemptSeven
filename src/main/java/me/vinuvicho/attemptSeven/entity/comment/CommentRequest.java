package me.vinuvicho.attemptSeven.entity.comment;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class CommentRequest {
    private String title;
    private String text;

    public CommentRequest() {
    }
}
