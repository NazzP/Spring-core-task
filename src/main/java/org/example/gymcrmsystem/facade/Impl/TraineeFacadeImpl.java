package org.example.gymcrmsystem.facade.Impl;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.facade.TraineeFacade;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeFacadeImpl implements TraineeFacade {

    private final TraineeService traineeService;

    @Autowired
    public TraineeFacadeImpl(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Override
    public TraineeDto createTrainee(TraineeDto traineeDTO) {
        return traineeService.create(traineeDTO);
    }

    @Override
    public TraineeDto getTraineeById(Long id) {
        return traineeService.select(id);
    }

    @Override
    public TraineeDto updateTrainee(Long id, TraineeDto traineeDTO) {
        return traineeService.update(id, traineeDTO);
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeService.delete(id);
    }
}
