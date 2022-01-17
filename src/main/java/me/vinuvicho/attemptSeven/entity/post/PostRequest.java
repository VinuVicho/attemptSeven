package me.vinuvicho.attemptSeven.entity.post;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
public class PostRequest {
    private String title;
    private String text;
    private String image;

    public PostRequest() {
    }
}
