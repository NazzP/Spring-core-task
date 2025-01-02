package org.example.gymcrmsystem.service.impl;

import jakarta.annotation.PostConstruct;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
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
        Map<Long, TrainingDto> trainings = parser.parseJsonToMap(trainingsFilePath, TrainingDto.class);
        for (TrainingDto trainingDto: trainings.values()){
            trainingRepository.save(trainingMapper.convertToEntity(trainingDto));
        }
    }

    @Override
    public TrainingDto create(TrainingDto trainingDto) {
        if (trainingRepository.findById(trainingDto.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Training with id " + trainingDto.getId() + " already exists");
        }
        return trainingMapper.convertToDto(trainingRepository.save(trainingMapper.convertToEntity(trainingDto)));
    }

    @Override
    public TrainingDto select(Long id) {
        return trainingMapper.convertToDto(trainingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Training with id " + id + " wasn't found")
        ));
    }
}
