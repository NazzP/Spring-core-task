package org.example.gymcrmsystem.repository.impl;

import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {

    private final Storage<Trainer> trainerStorage;

    @Autowired
    public TrainerRepositoryImpl(@Qualifier("trainerStorage") Storage<Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
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