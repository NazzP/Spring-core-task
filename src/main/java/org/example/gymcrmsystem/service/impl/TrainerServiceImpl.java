package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UsernameGenerator usernameGenerator;
    private final TrainerMapper trainerMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UsernameGenerator usernameGenerator, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.usernameGenerator = usernameGenerator;
        this.trainerMapper = trainerMapper;
    }


    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if (trainerDto != null) {
            Trainer trainer = trainerMapper.convertToEntity(trainerDto);
            trainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
            return trainerMapper.convertToDto(trainerRepository.saveNew(trainer));
        }
        throw new NullEntityReferenceException("Trainer cannot be 'null'");
    }

    @Override
    public TrainerDto select(Long id) {
        return trainerMapper.convertToDto(trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        ));
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        Trainer existingTrainer = trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        );
        existingTrainer.setFirstName(trainerDto.getFirstName());
        existingTrainer.setLastName(trainerDto.getLastName());
        existingTrainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        existingTrainer.setSpecialization(trainerDto.getSpecialization());

        return trainerMapper.convertToDto(trainerRepository.save(existingTrainer));
    }
}