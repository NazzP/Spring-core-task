package org.example.gymcrmsystem.dao.storage;

import org.example.gymcrmsystem.model.Trainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class TrainerStorageConfiguration {

    @Bean
    public Map<Long, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean
    public AtomicLong idCounterTrainer(Map<Long, Trainer> trainerStorage) {
        return new AtomicLong((long) trainerStorage.size() + 1);
    }
}