package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainerDTO;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerFacade {

    private final TrainerService trainerService;

    @Autowired
    public TrainerFacade(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public TrainerDTO createTrainer(TrainerDTO trainerDTO) {
        return trainerService.create(trainerDTO);
    }

    public TrainerDTO getTrainerById(Long id) {
        return trainerService.select(id);
    }

    public TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO) {
        return trainerService.update(id, trainerDTO);
    }
}
