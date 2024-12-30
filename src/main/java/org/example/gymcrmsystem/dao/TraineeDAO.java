package org.example.gymcrmsystem.dao;

import org.example.gymcrmsystem.model.Trainee;

import java.util.Optional;

public interface TraineeDAO{
    Trainee save(Trainee trainee);
    Optional<Trainee> findById(Long id);
    void deleteById(Long id);
}