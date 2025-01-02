package org.example.gymcrmsystem.repository.impl;

import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.model.Training;
import org.example.gymcrmsystem.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {

    private final Storage<Training> trainingStorage;

    @Autowired
    public TrainingRepositoryImpl(Storage<Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public Training save(Training training) {
        trainingStorage.save(training.getId(), training);
        return trainingStorage.get(training.getId());
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }
}