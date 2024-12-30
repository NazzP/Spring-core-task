package org.example.gymcrmsystem.dao;

import org.example.gymcrmsystem.model.Training;

import java.util.Optional;

public interface TrainingDAO{
    Training save(Training training);
    Optional<Training> findById(Long id);
}