package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeDAO;
    private final TraineeMapper traineeMapper;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeDAO, TraineeMapper traineeMapper) {
        this.traineeDAO = traineeDAO;
        this.traineeMapper = traineeMapper;
    }

    @Override
    public TraineeDto create(TraineeDto traineeDTO) {
        if (traineeDTO != null) {
            return traineeMapper.convertToDto(traineeDAO.save(traineeMapper.convertToEntity(traineeDTO)));
        }
        throw new NullObjectReferenceException("Trainee cannot be 'null'");
    }

    @Override
    public TraineeDto select(Long id) {
        return traineeMapper.convertToDto(traineeDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainee with id " + id + " wasn't found")
        ));
    }

    @Override
    public TraineeDto update(Long id, TraineeDto traineeDTO) {
        Trainee existingTrainee = traineeDAO.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Trainee with id " + id + " wasn't found")
        );
        existingTrainee.setFirstName(traineeDTO.getFirstName());
        existingTrainee.setLastName(traineeDTO.getLastName());
        existingTrainee.setUsername(traineeDTO.getUsername());
        existingTrainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        existingTrainee.setAddress(traineeDTO.getAddress());

        return traineeMapper.convertToDto((traineeDAO.save(existingTrainee)));
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
