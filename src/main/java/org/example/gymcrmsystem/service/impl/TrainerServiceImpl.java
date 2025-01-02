package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.service.TrainerService;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

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
        LOGGER.info("Initializing Trainer Service: Loading trainers from file {}", trainersFilePath);
        Map<Long, TrainerDto> trainers = parser.parseJsonToMap(trainersFilePath, TrainerDto.class);
        LOGGER.info("Loaded {} trainers from file {}", trainers.size(), trainersFilePath);

        for (TrainerDto trainerDto : trainers.values()) {
            trainerDto.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
            trainerRepository.save(trainerMapper.convertToEntity(trainerDto));
            LOGGER.info("Trainer with ID {} initialized and saved", trainerDto.getId());
        }
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        if (trainerDto == null) {
            LOGGER.debug("Attempted to create trainer with null input");
            throw new NullEntityReferenceException("Trainer cannot be null");
        }
        LOGGER.info("Attempting to create Trainer with ID {}", trainerDto.getId());

        if (trainerRepository.findById(trainerDto.getId()).isPresent()) {
            LOGGER.warn("Trainer with ID {} already exists", trainerDto.getId());
            throw new EntityAlreadyExistsException("Trainer with id " + trainerDto.getId() + " already exists");
        }

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);
        trainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        Trainer savedTrainer = trainerRepository.save(trainer);
        LOGGER.info("Created new Trainer with ID {}", savedTrainer.getId());

        return trainerMapper.convertToDto(savedTrainer);
    }

    @Override
    public TrainerDto select(Long id) {
        LOGGER.info("Selecting Trainer with ID {}", id);

        Trainer trainer = trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        );
        LOGGER.info("Trainer with ID {} found", id);

        return trainerMapper.convertToDto(trainer);
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        LOGGER.info("Updating Trainer with ID {}", id);

        Trainer existingTrainer = trainerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainer with id " + id + " wasn't found")
        );

        existingTrainer.setFirstName(trainerDto.getFirstName());
        existingTrainer.setLastName(trainerDto.getLastName());
        existingTrainer.setUsername(usernameGenerator.generateUniqueUsername(trainerDto));
        existingTrainer.setSpecialization(trainerDto.getSpecialization());

        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        LOGGER.info("Trainer with ID {} updated successfully", updatedTrainer.getId());

        return trainerMapper.convertToDto(updatedTrainer);
    }
}
