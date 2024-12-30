package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainingDTO;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingFacade {

    private final TrainingService trainingService;

    @Autowired
    public TrainingFacade(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    public TrainingDTO createTraining(TrainingDTO trainingDTO) {
        return trainingService.create(trainingDTO);
    }

    public TrainingDTO getTrainingById(Long id) {
        return trainingService.select(id);
    }
}
