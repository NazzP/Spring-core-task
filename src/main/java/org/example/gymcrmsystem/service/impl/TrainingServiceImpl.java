package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
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
