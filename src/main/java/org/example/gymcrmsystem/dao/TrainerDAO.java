package org.example.gymcrmsystem.dao;

import org.example.gymcrmsystem.model.Trainer;

import java.util.Optional;

public interface TrainerDAO {
    Trainer save(Trainer trainer);
    Optional<Trainer> findById(Long id);
}