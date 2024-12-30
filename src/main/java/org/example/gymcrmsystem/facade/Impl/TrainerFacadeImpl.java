package org.example.gymcrmsystem.facade.Impl;

import org.example.gymcrmsystem.dto.TrainerDTO;
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
    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {
        return trainerService.create(trainerDTO);
    }

    @Override
    public TrainerDTO getTrainerById(Long id) {
        return trainerService.select(id);
    }

    @Override
    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) {
        return trainerService.update(id, trainerDTO);
    }
}
