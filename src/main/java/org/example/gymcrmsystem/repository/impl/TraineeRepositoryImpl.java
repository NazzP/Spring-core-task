package org.example.gymcrmsystem.repository.impl;

import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {

    private final Storage<Trainee> traineeStorage;

    @Autowired
    public TraineeRepositoryImpl(Storage<Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public Trainee save(Trainee trainee) {
        traineeStorage.save(trainee.getId(),trainee);
        return traineeStorage.get(trainee.getId());
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public void deleteById(Long id) {
        traineeStorage.remove(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return traineeStorage.findAll()
                .stream()
                .anyMatch(trainee -> username.equals(trainee.getUsername()));
    }
}