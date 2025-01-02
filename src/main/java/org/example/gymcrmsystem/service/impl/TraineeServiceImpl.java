package org.example.gymcrmsystem.service.impl;

import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UsernameGenerator usernameGenerator;
    private final TraineeMapper traineeMapper;

    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, UsernameGenerator usernameGenerator, TraineeMapper traineeMapper) {
        this.traineeRepository = traineeRepository;
        this.usernameGenerator = usernameGenerator;
        this.traineeMapper = traineeMapper;
    }

    @Override
    public TraineeDto create(TraineeDto traineeDto) {
        if (traineeDto != null) {
            Trainee trainee = traineeMapper.convertToEntity(traineeDto);
            trainee.setUsername(usernameGenerator.generateUniqueUsername(traineeDto));
            return traineeMapper.convertToDto(traineeRepository.saveNew(trainee));
        }
        throw new NullEntityReferenceException("Trainee cannot be 'null'");
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
