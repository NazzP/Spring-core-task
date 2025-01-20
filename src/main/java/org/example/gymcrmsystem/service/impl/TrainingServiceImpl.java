package org.example.gymcrmsystem.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.entity.Training;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class TrainingServiceImpl implements TrainingService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingDto add(@Valid TrainingDto trainingDto) {
        String trainingTypeName = trainingDto.getTrainingType().getTrainingTypeName();
        TrainingType trainingType = trainingTypeRepository.findByName(trainingTypeName)
                .orElseThrow(() -> new EntityNotFoundException("TrainingType with name " + trainingTypeName + " wasn't found"));

        Trainer trainer = trainerRepository.findByUsername(trainingDto.getTrainer().getUser().getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Trainee trainee = traineeRepository.findByUsername(trainingDto.getTrainee().getUser().getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found"));

        Training training = trainingMapper.convertToEntity(trainingDto);
        training.setTrainingType(trainingType);
        training.setTrainer(trainer);
        training.setTrainee(trainee);

        Training addedTraining = trainingRepository.save(training);
        LOGGER.info("Added new Training with ID {}", addedTraining.getId());

        return trainingMapper.convertToDto(addedTraining);
    }

    public List<TrainingDto> getTraineeTrainingsListCriteria(String traineeUsername, LocalDate fromDate,
                                                             LocalDate toDate, String trainerName, String trainingType) {

        traineeRepository.findByUsername(traineeUsername).orElseThrow(
                () -> new EntityNotFoundException("Trainee with username " + traineeUsername + " wasn't found")
        );

        return trainingRepository.getByTraineeCriteria(traineeUsername, fromDate, toDate, trainerName, trainingType).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }

    public List<TrainingDto> getTrainerTrainingsListCriteria(String trainerUsername, LocalDate fromDate,
                                                             LocalDate toDate, String traineeName) {
        trainerRepository.findByUsername(trainerUsername).orElseThrow(
                () -> new EntityNotFoundException("Trainer with username " + trainerUsername + " wasn't found")
        );
        return trainingRepository.getByTrainerCriteria(trainerUsername, fromDate, toDate, traineeName).stream()
                .map(trainingMapper::convertToDto)
                .toList();
    }
}
