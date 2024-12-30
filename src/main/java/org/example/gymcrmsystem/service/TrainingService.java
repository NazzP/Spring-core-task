package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TrainingDTO;

public interface TrainingService {
    TrainingDTO create(TrainingDTO trainingDTO);
    TrainingDTO select(Long id);
}