package org.example.gymcrmsystem.dao.storage;

import org.example.gymcrmsystem.model.Trainee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class TraineeStorageConfiguration {

    @Bean
    public Map<Long, Trainee> traineeStorage() {
        return new HashMap<>();
    }

    @Bean
    public AtomicLong idCounterTrainee(Map<Long, Trainee> traineeStorage) {
        return new AtomicLong((long) traineeStorage.size() + 1);
    }
}