package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TraineeDTO;

public interface TraineeService {
    TraineeDTO create(TraineeDTO traineeDTO);
    TraineeDTO select(Long id);
    TraineeDTO update(Long id, TraineeDTO traineeDTO);
    void delete(Long id);
}