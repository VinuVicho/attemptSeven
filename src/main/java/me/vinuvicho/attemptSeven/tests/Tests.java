package me.vinuvicho.attemptSeven.tests;

import java.time.Duration;
import java.time.LocalDateTime;

public class Tests<T> {
    public static void main(String[] args) {
//        System.out.println(!new Validate().validateEmail("dd"));
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(Duration.between(now, LocalDateTime.now()).toMillis());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Duration duration = Duration.between(now, LocalDateTime.now());
        System.out.println(duration.toMillis());


    }
}
