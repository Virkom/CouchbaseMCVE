package org.example;

import io.micronaut.runtime.Micronaut;

public class Application {
    public static void main(String[] args) {
        Micronaut.build(new String[0])
                .classes(Application.class)
                .banner(false)
                .defaultEnvironments("dev")
                .start();
    }
}
