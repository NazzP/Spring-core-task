package org.example.gymcrmsystem.dao.impl;

import org.example.gymcrmsystem.dao.TrainingDAO;
import org.example.gymcrmsystem.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainingDAOImpl implements TrainingDAO {

    private final Map<Long, Training> trainingStorage;
    private final AtomicLong idCounterTraining;


    @Autowired
    public TrainingDAOImpl(Map<Long, Training> trainingStorage, AtomicLong idCounterTraining) {
        this.trainingStorage = trainingStorage;
        this.idCounterTraining = idCounterTraining;
    }

    @Override
    public Training save(Training training) {
        if (training.getId() == null) {
            training.setId(idCounterTraining.getAndIncrement());
        }
        trainingStorage.put(training.getId(), training);
        return trainingStorage.get(training.getId());
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }
}