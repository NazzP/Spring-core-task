package org.example.gymcrmsystem.repository.impl;

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
    public Trainer save(Trainer trainer) {
        trainerStorage.save(trainer.getId(), trainer);
        return trainerStorage.get(trainer.getId());
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }
}