package org.example.gymcrmsystem.facade.Impl;

import org.example.gymcrmsystem.dto.TrainingDTO;
import org.example.gymcrmsystem.facade.TrainingFacade;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;

    @Autowired
    public TrainingFacadeImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public TrainingDTO createTraining(TrainingDTO trainingDTO) {
        return trainingService.create(trainingDTO);
    }

    @Override
    public TrainingDTO getTrainingById(Long id) {
        return trainingService.select(id);
    }
}
