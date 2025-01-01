package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
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
        if (trainingDto != null) {
            return trainingMapper.convertToDto(trainingRepository.save(trainingMapper.convertToEntity(trainingDto)));
        }
        throw new NullEntityReferenceException("Training cannot be 'null'");
    }

    @Override
    public TrainingDto select(Long id) {
        return trainingMapper.convertToDto(trainingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Training with id " + id + " wasn't found")
        ));
    }
}
