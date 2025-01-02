package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final JsonStorageParser<Long, TrainerDto> parser;
    private final TrainerRepository trainerRepository;
    private final UsernameGenerator usernameGenerator;
    private final TrainerMapper trainerMapper;

    @Value("${data.file.trainers}")
    private String trainersFilePath;

    @Autowired
    public TrainerServiceImpl(JsonStorageParser<Long, TrainerDto> parser, TrainerRepository trainerRepository, UsernameGenerator usernameGenerator, TrainerMapper trainerMapper) {
        this.parser = parser;
        this.trainerRepository = trainerRepository;
        this.usernameGenerator = usernameGenerator;
        this.trainerMapper = trainerMapper;
    }

    @PostConstruct
    private void initialize() {
        Map<Long, TrainerDto> trainers = parser.parseJsonToMap(trainersFilePath, TrainerDto.class);
        for (TrainerDto trainerDto: trainers.values()){
            usernameGenerator.generateUniqueUsername(trainerDto);
            trainerRepository.save(trainerMapper.convertToEntity(trainerDto));
        }
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if (trainerRepository.findById(trainerDto.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Trainer with id " + trainerDto.getId() + " already exists");
        }
        Trainer trainer = trainerMapper.convertToEntity(trainerDto);
        trainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        return trainerMapper.convertToDto(trainerRepository.save(trainer));
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