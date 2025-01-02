package org.example.gymcrmsystem.config;

import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.Training;
import org.example.gymcrmsystem.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public Storage<Trainee> traineeStorage() {
        return new Storage<>();
    }

    @Bean
    public Storage<Trainer> trainerStorage() {
        return new Storage<>();
    }

    @Bean
    public Storage<Training> trainingStorage() {
        return new Storage<>();
    }
}
