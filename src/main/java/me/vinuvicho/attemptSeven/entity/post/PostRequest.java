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
    private final String title;
    private final String text;
    private final String image;

}
