package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.dao.TraineeDAO;
import org.example.gymcrmsystem.dto.TraineeDTO;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    public TraineeDTO create(TraineeDTO traineeDTO) {
        if (traineeDTO != null) {
            return TraineeDTO.fromEntity(traineeDAO.save(traineeDTO.toEntity()));
        }
        throw new NullObjectReferenceException("Trainee cannot be 'null'");
    }

    @Override
    public TraineeDTO select(Long id) {
        return TraineeDTO.fromEntity(traineeDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainee with id " + id + " wasn't found")
        ));
    }

    @Override
    public TraineeDTO update(Long id, TraineeDTO traineeDTO) {
        Trainee existingTrainee = traineeDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainee with id " + id + " wasn't found")
        );
        existingTrainee.setFirstName(traineeDTO.getUserDTO().getFirstName());
        existingTrainee.setLastName(traineeDTO.getUserDTO().getLastName());
        existingTrainee.setUsername(traineeDTO.getUserDTO().getUsername());
        existingTrainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        existingTrainee.setAddress(traineeDTO.getAddress());

        return TraineeDTO.fromEntity(traineeDAO.save(existingTrainee));
    }

    @Override
    public void delete(Long id) {
        if (traineeDAO.findById(id).isPresent()) {
            traineeDAO.deleteById(id);
            return;
        }
        throw new ObjectNotFoundException("Trainee with id " + id + " wasn't found");
    }
}
