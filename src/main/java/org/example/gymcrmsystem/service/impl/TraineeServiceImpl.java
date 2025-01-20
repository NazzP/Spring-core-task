package org.example.gymcrmsystem.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.service.TraineeService;
import org.example.gymcrmsystem.utils.PasswordGenerator;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;
    private final TraineeMapper traineeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TraineeDto create(@Valid TraineeDto traineeDto) {
        traineeDto.getUser().setPassword(passwordEncoder.encode(passwordGenerator.generateRandomPassword()));
        traineeDto.getUser().setUsername(usernameGenerator.generateUniqueUsername(traineeDto.getUser()));

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);

        Trainee savedTrainee = traineeRepository.save(trainee);
        LOGGER.info("Trainee created with ID {}", savedTrainee.getId());

        return traineeMapper.convertToDto(savedTrainee);
    }

    @Override
    public TraineeDto select(String username) {
        LOGGER.info("Selecting trainee with username {}", username);
        Trainee trainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> {
                    LOGGER.debug("Trainee with username {} not found", username);
                    return new EntityNotFoundException("Trainee with username " + username + " wasn't found");
                }
        );
        return traineeMapper.convertToDto(trainee);
    }

    @Override
    public TraineeDto update(String username, @Valid TraineeDto traineeDto) {
        LOGGER.info("Updating trainee with Username {}", username);
        Trainee existingTrainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );

        existingTrainee.getUser().setFirstName(traineeDto.getUser().getFirstName());
        existingTrainee.getUser().setLastName(traineeDto.getUser().getLastName());
        existingTrainee.getUser().setUsername(traineeDto.getUser().getUsername());
        existingTrainee.setDateOfBirth(traineeDto.getDateOfBirth());
        existingTrainee.setAddress(traineeDto.getAddress());

        Trainee updatedTrainee = traineeRepository.save(existingTrainee);
        LOGGER.info("Trainee with ID {} updated", updatedTrainee.getId());
        return traineeMapper.convertToDto(updatedTrainee);
    }

    @Override
    public void delete(String username) {
        LOGGER.info("Deleting trainee with username {}", username);
        traineeRepository.findByUsername(username).ifPresentOrElse(
                trainee -> {
                    traineeRepository.deleteByUsername(username);
                    LOGGER.info("Trainee with username {} deleted", username);
                },
                () -> {
                    LOGGER.debug("Trainee with username {} wasn't found", username);
                    throw new EntityNotFoundException("Trainee with username " + username + " wasn't found");
                }
        );
    }

    @Override
    public boolean authenticateTrainee(String username, String password) {
        Trainee existingTrainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );
        return passwordEncoder.matches(password, existingTrainee.getUser().getPassword());
    }

    @Override
    public void changePassword(String username, String lastPassword, String newPassword) {
        Trainee existingTrainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );
        if (passwordEncoder.matches(lastPassword, existingTrainee.getUser().getPassword())) {
            existingTrainee.getUser().setPassword(passwordEncoder.encode(newPassword));
            traineeRepository.save(existingTrainee);
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    @Override
    public void changeStatus(String username, Boolean isActive) {
        Trainee existingTrainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );
        existingTrainee.getUser().setIsActive(isActive);
        traineeRepository.save(existingTrainee);
    }

    @Override
    public String forgotPassword(String username) {
        Trainee existingTrainee = traineeRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + username + " wasn't found")
        );

        String tempPassword = passwordGenerator.generateRandomPassword();

        existingTrainee.getUser().setPassword(passwordEncoder.encode(tempPassword));
        traineeRepository.save(existingTrainee);

        return tempPassword;
    }
}
