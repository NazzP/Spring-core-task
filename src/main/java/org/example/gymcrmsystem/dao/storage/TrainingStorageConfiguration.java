package org.example.gymcrmsystem.dao.storage;

import org.example.gymcrmsystem.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class TrainingStorageConfiguration {

    @Bean
    public Map<Long, Training> trainingStorage() {
        return new HashMap<>();
    }

    @Bean
    public AtomicLong idCounterTraining(Map<Long, Training> trainingStorage) {
        return new AtomicLong((long) trainingStorage.size() + 1);
    }
}