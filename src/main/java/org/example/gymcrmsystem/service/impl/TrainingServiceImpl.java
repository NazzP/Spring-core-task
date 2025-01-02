package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final JsonStorageParser<Long, TrainingDto> parser;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Value("${data.file.trainings}")
    private String trainingsFilePath;

    @Autowired
    public TrainingServiceImpl(JsonStorageParser<Long, TrainingDto> parser, TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.parser = parser;
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    @PostConstruct
    private void initialize() {
        LOGGER.info("Initializing Training Service: Loading trainings from file {}", trainingsFilePath);
        Map<Long, TrainingDto> trainings = parser.parseJsonToMap(trainingsFilePath, TrainingDto.class);
        LOGGER.info("Loaded {} trainings from file {}", trainings.size(), trainingsFilePath);

        for (TrainingDto trainingDto : trainings.values()) {
            trainingRepository.save(trainingMapper.convertToEntity(trainingDto));
            LOGGER.info("Training with ID {} initialized and saved", trainingDto.getId());
        }
    }

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        if (trainingDto == null) {
            LOGGER.debug("Attempted to create training with null input.");
            throw new NullEntityReferenceException("Training cannot be null");
        }
        LOGGER.info("Attempting to create Training with ID {}", trainingDto.getId());

        if (trainingRepository.findById(trainingDto.getId()).isPresent()) {
            LOGGER.warn("Training with ID {} already exists", trainingDto.getId());
            throw new EntityAlreadyExistsException("Training with id " + trainingDto.getId() + " already exists");
        }

        TrainingDto savedTrainingDto = trainingMapper.convertToDto(trainingRepository.save(trainingMapper.convertToEntity(trainingDto)));
        LOGGER.info("Created new Training with ID {}", savedTrainingDto.getId());

        return savedTrainingDto;
    }

    @Override
    public TrainingDto select(Long id) {
        LOGGER.info("Selecting Training with ID {}", id);
        TrainingDto trainingDto = trainingMapper.convertToDto(trainingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Training with id " + id + " wasn't found")
        ));
        LOGGER.info("Training with ID {} found", id);
        return trainingDto;
    }
}