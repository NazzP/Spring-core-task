package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.parser.JsonStorageParser;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TraineeServiceImpl implements TraineeService {

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
        Map<Long, TraineeDto> trainees = parser.parseJsonToMap(traineesFilePath, TraineeDto.class);
        for (TraineeDto traineeDto: trainees.values()){
            usernameGenerator.generateUniqueUsername(traineeDto);
            traineeRepository.save(traineeMapper.convertToEntity(traineeDto));
        }
    }

    @Override
    public TraineeDto create(TraineeDto traineeDto) {
        if (traineeRepository.findById(traineeDto.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Trainee with id " + traineeDto.getId() + " already exists");
        }
        Trainee trainee = traineeMapper.convertToEntity(traineeDto);
        trainee.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
        return traineeMapper.convertToDto(traineeRepository.save(trainee));
    }

    @Override
    public TraineeDto select(Long id) {
        return traineeMapper.convertToDto(traineeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainee with id " + id + " wasn't found")
        ));
    }

    @Override
    public TraineeDto update(Long id, TraineeDto traineeDto) {
        Trainee existingTrainee = traineeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trainee with id " + id + " wasn't found")
        );
        existingTrainee.setFirstName(traineeDto.getFirstName());
        existingTrainee.setLastName(traineeDto.getLastName());
        existingTrainee.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
        existingTrainee.setDateOfBirth(traineeDto.getDateOfBirth());
        existingTrainee.setAddress(traineeDto.getAddress());

        return traineeMapper.convertToDto((traineeRepository.save(existingTrainee)));
    }

    @Override
    public void delete(Long id) {
        if (traineeRepository.findById(id).isPresent()) {
            traineeRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Trainee with id " + id + " wasn't found");
    }
}
