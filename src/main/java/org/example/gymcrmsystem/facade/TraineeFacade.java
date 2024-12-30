package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TraineeDTO;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeFacade {

    private final TraineeService traineeService;

    @Autowired
    public TraineeFacade(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    public TraineeDTO createTrainee(TraineeDTO traineeDTO) {
        return traineeService.create(traineeDTO);
    }

    public TraineeDTO getTraineeById(Long id) {
        return traineeService.select(id);
    }

    public TraineeDTO updateTrainee(Long id, TraineeDTO traineeDTO) {
        return traineeService.update(id, traineeDTO);
    }

    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }
}
