package org.example.gymcrmsystem.repository.impl;

import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.storage.TrainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    private final TrainerStorage trainerStorage;

    @Autowired
    public TrainerRepositoryImpl(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public Trainer saveNew(Trainer trainer) {
        Trainer existingTrainer = trainerStorage.saveNew(trainer);
        if (existingTrainer != null) {
            throw new EntityAlreadyExistsException("A trainee with ID " + trainer.getId() + " already exists");
        }
        return trainerStorage.get(trainer.getId());
    }

    @Override
    public Trainer save(Trainer trainer) {
        trainerStorage.save(trainer.getId(), trainer);
        return trainerStorage.get(trainer.getId());
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    @Override
    public boolean existsByUsername(String username) {
        return trainerStorage.findAll()
                .stream()
                .anyMatch(trainee -> username.equals(trainee.getUsername()));
    }
}