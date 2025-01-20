package org.example.gymcrmsystem.facade.impl;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.facade.TraineeFacade;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineeFacadeImpl implements TraineeFacade {

    private final TraineeService traineeService;

    @Override
    public TraineeDto createTrainee(TraineeDto traineeDto) {
        return traineeService.create(traineeDto);
    }

    @Override
    public TraineeDto getTraineeById(Long id) {
        return traineeService.select(id);
    }

    @Override
    public TraineeDto updateTrainee(Long id, TraineeDto traineeDto) {
        return traineeService.update(id, traineeDto);
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }
}
