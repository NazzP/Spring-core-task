package org.example.gymcrmsystem.storage;

import org.example.gymcrmsystem.model.Training;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingStorage {

    private final Map<Long, Training> trainingMap;

    public TrainingStorage() {
        trainingMap = new HashMap<>();
    }

    public Training save(Training training) {
        return trainingMap.put(training.getId(), training);
    }

    public Training get(Long id) {
        return trainingMap.get(id);
    }
}