package me.vinuvicho.attemptSeven.entity.post;

import java.util.Comparator;

@SuppressWarnings("ComparatorMethodParameterNotUsed")
public class PostComparator implements Comparator<Post> {

    @Override
    public int compare(Post post1, Post post2) {
        return (post1.getPostedAt().isBefore(post2.getPostedAt())) ? -1 : 1;
    }
}
