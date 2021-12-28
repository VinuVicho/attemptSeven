package me.vinuvicho.attemptSeven;

import me.vinuvicho.attemptSeven.registration.Validate;

public class Tests {
    public static void main(String[] args) {
        System.out.println(!new Validate().validateEmail("dd"));
    }
}
