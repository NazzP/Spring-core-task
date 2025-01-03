package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

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
        log.info("Initializing Training Service: Loading trainings from file {}", trainingsFilePath);
        Map<Long, TrainingDto> trainings = parser.parseJsonToMap(trainingsFilePath, TrainingDto.class);
        log.info("Loaded {} trainings from file {}", trainings.size(), trainingsFilePath);

        for (TrainingDto trainingDto : trainings.values()) {
            trainingRepository.save(trainingMapper.convertToEntity(trainingDto));
            log.info("Training with ID {} initialized and saved", trainingDto.getId());
        }
    }

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        if (trainingDto == null) {
            log.debug("Attempted to create training with null input.");
            throw new NullEntityReferenceException("Training cannot be null");
        }
        log.info("Attempting to create Training with ID {}", trainingDto.getId());

        if (trainingRepository.findById(trainingDto.getId()).isPresent()) {
            log.warn("Training with ID {} already exists", trainingDto.getId());
            throw new EntityAlreadyExistsException("Training with id " + trainingDto.getId() + " already exists");
        }

        TrainingDto savedTrainingDto = trainingMapper.convertToDto(trainingRepository.save(trainingMapper.convertToEntity(trainingDto)));
        log.info("Created new Training with ID {}", savedTrainingDto.getId());

        return savedTrainingDto;
    }

    @Override
    public TrainingDto select(Long id) {
        log.info("Selecting Training with ID {}", id);
        TrainingDto trainingDto = trainingMapper.convertToDto(trainingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Training with id " + id + " wasn't found")
        ));
        log.info("Training with ID {} found", id);
        return trainingDto;
    }
}
