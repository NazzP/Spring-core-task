package org.example.gymcrmsystem.facade.impl;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.facade.TrainerFacade;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainerFacadeImpl implements TrainerFacade {

    private final TrainerService trainerService;

    @Override
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        return trainerService.create(trainerDto);
    }

    @Override
    public TrainerDto getTrainerById(Long id) {
        return trainerService.select(id);
    }

    @Override
    public TrainerDto updateTrainer(Long id, TrainerDto trainerDto) {
        return trainerService.update(id, trainerDto);
    }
}
