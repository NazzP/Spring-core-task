package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.example.gymcrmsystem.utils.PasswordGenerator;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
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
        log.info("Initializing Trainer Service: Loading trainers from file {}", trainersFilePath);
        Map<Long, TrainerDto> trainers = parser.parseJsonToMap(trainersFilePath, TrainerDto.class);
        log.info("Loaded {} trainers from file {}", trainers.size(), trainersFilePath);

        for (TrainerDto trainerDto : trainers.values()) {
            trainerDto.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
            trainerDto.setPassword(PasswordGenerator.generateRandomPassword());
            trainerRepository.save(trainerMapper.convertToEntity(trainerDto));
            log.info("Trainer with ID {} initialized and saved", trainerDto.getId());
        }
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if (trainerDto == null) {
            log.debug("Attempted to create trainer with null input");
            throw new NullEntityReferenceException("Trainer cannot be null");
        }
        log.info("Attempting to create Trainer with ID {}", trainerDto.getId());

        if (trainerRepository.findById(trainerDto.getId()).isPresent()) {
            log.warn("Trainer with ID {} already exists", trainerDto.getId());
            throw new EntityAlreadyExistsException("Trainer with id " + trainerDto.getId() + " already exists");
        }

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);
        trainer.setPassword(PasswordGenerator.generateRandomPassword());
        trainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Created new Trainer with ID {}", savedTrainer.getId());

        return trainerMapper.convertToDto(savedTrainer);
    }

    @Override
    public TrainerDto select(Long id) {
        log.info("Selecting Trainer with ID {}", id);

        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        );
        log.info("Trainer with ID {} found", id);

        return trainerMapper.convertToDto(trainer);
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        log.info("Updating Trainer with ID {}", id);

        Trainer existingTrainer = trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        );

        existingTrainer.setFirstName(trainerDto.getFirstName());
        existingTrainer.setLastName(trainerDto.getLastName());
        existingTrainer.setPassword(trainerDto.getPassword());
        existingTrainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        existingTrainer.setSpecialization(trainerDto.getSpecialization());

        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        log.info("Trainer with ID {} updated successfully", updatedTrainer.getId());

        return trainerMapper.convertToDto(updatedTrainer);
    }
}
