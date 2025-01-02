package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.example.gymcrmsystem.utils.PasswordGenerator;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final JsonStorageParser<Long, TraineeDto> parser;
    private final TraineeRepository traineeRepository;
    private final UsernameGenerator usernameGenerator;
    private final TraineeMapper traineeMapper;

    @Value("${data.file.trainees}")
    private String traineesFilePath;

    @Autowired
    public TraineeServiceImpl(JsonStorageParser<Long, TraineeDto> parser, TraineeRepository traineeRepository, UsernameGenerator usernameGenerator, TraineeMapper traineeMapper) {
        this.parser = parser;
        this.traineeRepository = traineeRepository;
        this.usernameGenerator = usernameGenerator;
        this.traineeMapper = traineeMapper;
    }

    @PostConstruct
    private void initialize() {
        LOGGER.info("Initializing trainees from file: {}", traineesFilePath);
        Map<Long, TraineeDto> trainees = parser.parseJsonToMap(traineesFilePath, TraineeDto.class);
        LOGGER.info("Loaded {} trainees from file {}", trainees.size(), traineesFilePath);
        for (TraineeDto traineeDto : trainees.values()) {
            traineeDto.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
            traineeDto.setPassword(PasswordGenerator.generateRandomPassword());
            traineeRepository.save(traineeMapper.convertToEntity(traineeDto));
            LOGGER.info("Trainee {} initialized with username {}", traineeDto.getFirstName(), traineeDto.getUsername());
        }
    }

    @Override
    public TraineeDto create(TraineeDto traineeDto) {
        if (traineeDto == null) {
            LOGGER.debug("Attempted to create trainee with null input");
            throw new NullEntityReferenceException("Trainee cannot be null");
        }
        LOGGER.info("Creating trainee with ID {}", traineeDto.getId());
        if (traineeRepository.findById(traineeDto.getId()).isPresent()) {
            LOGGER.debug("Trainee with ID {} already exists", traineeDto.getId());
            throw new EntityAlreadyExistsException("Trainee with id " + traineeDto.getId() + " already exists");
        }
        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
        trainee.setPassword(PasswordGenerator.generateRandomPassword());
        trainee.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
        Trainee savedTrainee = traineeRepository.save(trainee);
        LOGGER.info("Trainee created with ID {}", savedTrainee.getId());
        return traineeMapper.convertToDto(savedTrainee);
    }

    @Override
    public TraineeDto select(Long id) {
        LOGGER.info("Selecting trainee with ID {}", id);
        Trainee trainee = traineeRepository.findById(id).orElseThrow(
                () -> {
                    LOGGER.debug("Trainee with ID {} not found", id);
                    return new EntityNotFoundException("Trainee with id " + id + " wasn't found");
                }
        );
        return traineeMapper.convertToDto(trainee);
    }

    @Override
    public TraineeDto update(Long id, TraineeDto traineeDto) {
        LOGGER.info("Updating trainee with ID {}", id);
        Trainee existingTrainee = traineeRepository.findById(id).orElseThrow(
                () -> {
                    LOGGER.debug("Trainee with ID {} wasn't found", id);
                    return new EntityNotFoundException("Trainee with id " + id + " wasn't found");
                }
        );
        existingTrainee.setFirstName(traineeDto.getFirstName());
        existingTrainee.setLastName(traineeDto.getLastName());
        existingTrainee.setPassword(traineeDto.getPassword());
        existingTrainee.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
        existingTrainee.setDateOfBirth(traineeDto.getDateOfBirth());
        existingTrainee.setAddress(traineeDto.getAddress());

        Trainee updatedTrainee = traineeRepository.save(existingTrainee);
        LOGGER.info("Trainee with ID {} updated", updatedTrainee.getId());
        return traineeMapper.convertToDto(updatedTrainee);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting trainee with ID {}", id);
        if (traineeRepository.findById(id).isPresent()) {
            traineeRepository.deleteById(id);
            LOGGER.info("Trainee with ID {} deleted", id);
        } else {
            LOGGER.debug("Trainee with ID {} isn't found", id);
            throw new EntityNotFoundException("Trainee with id " + id + " wasn't found");
        }
    }
}