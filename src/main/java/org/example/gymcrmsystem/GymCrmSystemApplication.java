package org.example.gymcrmsystem;

import org.example.gymcrmsystem.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCrmSystemApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "prod");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
        }
    }
}
