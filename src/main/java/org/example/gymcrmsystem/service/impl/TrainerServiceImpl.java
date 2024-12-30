package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.dao.TrainerDAO;
import org.example.gymcrmsystem.dto.TrainerDTO;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }


    @Override
    public TrainerDTO create(TrainerDTO trainerDTO) {
        if (trainerDTO != null) {
            return TrainerDTO.fromEntity(trainerDAO.save(trainerDTO.toEntity()));
        }
        throw new NullObjectReferenceException("Trainer cannot be 'null'");
    }

    @Override
    public TrainerDTO select(Long id) {
        return TrainerDTO.fromEntity(trainerDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        ));
    }

    @Override
    public TrainerDTO update(Long id, TrainerDTO trainerDTO) {
        Trainer existingTrainer = trainerDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        );
        existingTrainer.setFirstName(trainerDTO.getUserDTO().getFirstName());
        existingTrainer.setLastName(trainerDTO.getUserDTO().getLastName());
        existingTrainer.setUsername(trainerDTO.getUserDTO().getUsername());
        existingTrainer.setSpecialization(trainerDTO.getSpecialization().toEntity());

        return TrainerDTO.fromEntity(trainerDAO.save(existingTrainer));
    }
}