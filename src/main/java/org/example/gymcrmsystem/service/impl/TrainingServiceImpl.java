package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingDAO;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingDAO, TrainingMapper trainingMapper) {
        this.trainingDAO = trainingDAO;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public TrainingDto create(TrainingDto trainingDTO) {
        if (trainingDTO != null) {
            return trainingMapper.convertToDto(trainingDAO.save(trainingMapper.convertToEntity(trainingDTO)));
        }
        throw new NullObjectReferenceException("Training cannot be 'null'");
    }

    @Override
    public TrainingDto select(Long id) {
        return trainingMapper.convertToDto(trainingDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Training with id " + id + " wasn't found")
        ));
    }
}
