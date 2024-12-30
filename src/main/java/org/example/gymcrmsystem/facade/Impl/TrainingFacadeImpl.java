package org.example.gymcrmsystem.facade.Impl;

import org.example.gymcrmsystem.dto.TrainingDto;
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
    public TrainingDto createTraining(TrainingDto trainingDTO) {
        return trainingService.create(trainingDTO);
    }

    @Override
    public TrainingDto getTrainingById(Long id) {
        return trainingService.select(id);
    }
}
