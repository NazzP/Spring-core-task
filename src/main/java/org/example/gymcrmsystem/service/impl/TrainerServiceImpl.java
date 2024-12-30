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

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }


    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if (trainerDto != null) {
            return trainerMapper.convertToDto(trainerRepository.save(trainerMapper.convertToEntity(trainerDto)));
        }
        throw new NullObjectReferenceException("Trainer cannot be 'null'");
    }

    @Override
    public TrainerDto select(Long id) {
        return trainerMapper.convertToDto(trainerRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        ));
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        Trainer existingTrainer = trainerRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainer with id " + id + " wasn't found")
        );
        existingTrainer.setFirstName(trainerDto.getFirstName());
        existingTrainer.setLastName(trainerDto.getLastName());
        existingTrainer.setUsername(trainerDto.getUsername());
        existingTrainer.setSpecialization(trainerDto.getSpecialization());

        return trainerMapper.convertToDto(trainerRepository.save(existingTrainer));
    }
}