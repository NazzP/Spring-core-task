package org.example.gymcrmsystem.storage;

import org.example.gymcrmsystem.model.Trainee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TraineeStorage {

    private final Map<Long, Trainee> traineeMap;

    public TraineeStorage() {
        traineeMap = new HashMap<>();
    }

    public void save(Long id, Trainee trainee) {
        traineeMap.put(id, trainee);
    }

    public Trainee get(Long id) {
        return traineeMap.get(id);
    }

    public void remove(Long id) {
        traineeMap.remove(id);
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(traineeMap.values());
    }
}