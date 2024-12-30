package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.dao.TrainingDAO;
import org.example.gymcrmsystem.dto.TrainingDTO;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }


    @Override
    public TrainingDTO create(TrainingDTO trainingDTO) {
        if (trainingDTO != null) {
            return TrainingDTO.fromEntity(trainingDAO.save(trainingDTO.toEntity()));
        }
        throw new NullObjectReferenceException("Training cannot be 'null'");
    }

    @Override
    public TrainingDTO select(Long id) {
        return TrainingDTO.fromEntity(trainingDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Training with id " + id + " wasn't found")
        ));
    }
}
