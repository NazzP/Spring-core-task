package org.example.gymcrmsystem.storage;

import org.example.gymcrmsystem.model.Trainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TrainerStorage {

    private final Map<Long, Trainer> trainerMap;

    public TrainerStorage() {
        trainerMap = new HashMap<>();
    }

    public Trainer saveNew(Trainer trainer) {
        return trainerMap.putIfAbsent(trainer.getId(), trainer);
    }

    public void save(Long id, Trainer trainer) {
        trainerMap.put(id, trainer);
    }

    public Trainer get(Long id) {
        return trainerMap.get(id);
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(trainerMap.values());
    }
}