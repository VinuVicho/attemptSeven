package me.vinuvicho.attemptSeven.entity.post;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PostRequest {
    private String title;
    private String text;
    private String image;

    public PostRequest() {
    }
}
