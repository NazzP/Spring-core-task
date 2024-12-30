package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TrainerDTO;

public interface TrainerService {
    TrainerDTO create(TrainerDTO trainerDTO);
    TrainerDTO select(Long id);
    TrainerDTO update(Long id, TrainerDTO trainerDTO);
}