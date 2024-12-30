package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerDAO;
    private final TrainerMapper trainerMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerDAO, TrainerMapper trainerMapper) {
        this.trainerDAO = trainerDAO;
        this.trainerMapper = trainerMapper;
    }


    @Override
    public TrainerDto create(TrainerDto trainerDTO) {
        if (trainerDTO != null) {
            return trainerMapper.convertToDto(trainerDAO.save(trainerMapper.convertToEntity(trainerDTO)));
        }
        throw new NullObjectReferenceException("Trainer cannot be 'null'");
    }

    @Override
    public TrainerDto select(Long id) {
        return trainerMapper.convertToDto(trainerDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        ));
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDTO) {
        Trainer existingTrainer = trainerDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        );
        existingTrainer.setFirstName(trainerDTO.getFirstName());
        existingTrainer.setLastName(trainerDTO.getLastName());
        existingTrainer.setUsername(trainerDTO.getUsername());
        existingTrainer.setSpecialization(trainerDTO.getSpecialization());

        return trainerMapper.convertToDto(trainerDAO.save(existingTrainer));
    }
}