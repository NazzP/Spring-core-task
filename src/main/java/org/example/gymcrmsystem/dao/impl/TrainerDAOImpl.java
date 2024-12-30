package org.example.gymcrmsystem.dao.impl;

import org.example.gymcrmsystem.dao.TrainerDAO;
import org.example.gymcrmsystem.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    private final Map<Long, Trainer> trainerStorage;
    private final AtomicLong idCounterTrainer;


    @Autowired
    public TrainerDAOImpl(Map<Long, Trainer> trainerStorage, AtomicLong idCounterTrainer) {
        this.trainerStorage = trainerStorage;
        this.idCounterTrainer = idCounterTrainer;
    }

    @Override
    public Trainer save(Trainer trainer) {
        if (trainer.getId() == null) {
            trainer.setId(idCounterTrainer.getAndIncrement());
        }
        trainerStorage.put(trainer.getId(), trainer);
        return trainerStorage.get(trainer.getId());
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }
}