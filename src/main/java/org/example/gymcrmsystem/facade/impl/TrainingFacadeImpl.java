package org.example.gymcrmsystem.facade.impl;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.facade.TrainingFacade;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingFacadeImpl implements TrainingFacade {

    private final TrainingService trainingService;

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        return trainingService.create(trainingDto);
    }

    @Override
    public TrainingDto getTrainingById(Long id) {
        return trainingService.select(id);
    }
}
