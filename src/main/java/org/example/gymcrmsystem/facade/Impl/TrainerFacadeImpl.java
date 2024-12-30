package org.example.gymcrmsystem.facade.Impl;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.facade.TrainerFacade;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerFacadeImpl implements TrainerFacade {

    private final TrainerService trainerService;

    @Autowired
    public TrainerFacadeImpl(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Override
    public TrainerDto createTrainer(TrainerDto trainerDTO) {
        return trainerService.create(trainerDTO);
    }

    @Override
    public TrainerDto getTrainerById(Long id) {
        return trainerService.select(id);
    }

    @Override
    public TrainerDto updateTrainer(Long id, TrainerDto trainerDTO) {
        return trainerService.update(id, trainerDTO);
    }
}
