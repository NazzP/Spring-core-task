package org.example.gymcrmsystem.service;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.dto.*;
import org.example.gymcrmsystem.entity.*;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrainingServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    TrainingDto trainingDto;
    TraineeDto traineeDto;
    TrainerDto trainerDto;

    Trainee trainee;
    Trainer trainer;
    Training training;
    TrainingType trainingType;


    @BeforeEach
    void setup() {
        traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username("trainee1")
                        .password("pass")
                        .build())
                .dateOfBirth(LocalDate.of(2022, 1, 1))
                .address("123 Main srt")
                .build();

        trainerDto = TrainerDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("trainer1")
                        .password("pass")
                        .build())
                .specialization(new TrainingTypeDto("Yoga"))
                .build();

        trainingDto = TrainingDto.builder()
                .trainer(trainerDto)
                .trainee(traineeDto)
                .trainingType(new TrainingTypeDto("Yoga"))
                .build();

        trainingType = TrainingType.builder()
                .trainingTypeName("Yoga")
                .build();

        trainer = Trainer.builder()
                .user(User.builder().username("trainer1").build())
                .build();

        trainee = Trainee.builder()
                .user(User.builder().username("trainee1").build())
                .build();

        traineeRepository.save(trainee);
        trainerRepository.save(trainer);

        training = Training.builder()
                .trainer(trainer)
                .trainee(trainee)
                .trainingType(trainingType)
                .build();

        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.of(trainingType));
        when(trainerRepository.findByUsername(any())).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername(any())).thenReturn(Optional.of(trainee));
        when(trainingRepository.save(any())).thenReturn(training);

        when(trainingMapper.convertToEntity(any())).thenReturn(training);
        when(trainingMapper.convertToDto(any())).thenReturn(trainingDto);
    }

    @Test
    void addTrainingSuccess() {
        TrainingDto createdTraining = trainingService.add(trainingDto);

        assertNotNull(createdTraining);
        assertEquals("trainer1", createdTraining.getTrainer().getUser().getUsername());
        assertEquals("trainee1", createdTraining.getTrainee().getUser().getUsername());
        assertEquals("Yoga", createdTraining.getTrainingType().getTrainingTypeName());

        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void addTrainingTrainingTypeNotFound() {
        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> trainingService.add(trainingDto));
        assertEquals("TrainingType with name Yoga wasn't found", exception.getMessage());

        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void getTraineeTrainingsListCriteriaSuccess() {
        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 1, 1);

        List<Training> trainings = List.of(Training.builder()
                .trainer(Trainer.builder().user(User.builder().username("trainer1").build()).build())
                .trainee(Trainee.builder().user(User.builder().username("trainee1").build()).build())
                .trainingType(TrainingType.builder().trainingTypeName("Yoga").build())
                .build());

        when(trainingRepository.getByTraineeCriteria(any(), any(), any(), any(), any())).thenReturn(trainings);
        when(trainingMapper.convertToDto(any())).thenReturn(trainingDto);

        List<TrainingDto> result = trainingService.getTraineeTrainingsListCriteria("trainee1", fromDate, toDate, "trainer1", "Yoga");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getTrainingType().getTrainingTypeName());
        assertEquals("trainer1", result.get(0).getTrainer().getUser().getUsername());

        verify(trainingRepository, times(1)).getByTraineeCriteria(any(), any(), any(), any(), any());
    }

    @Test
    void getTrainerTrainingsListCriteriaSuccess() {
        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 1, 1);

        List<Training> trainings = List.of(Training.builder()
                .trainer(Trainer.builder().user(User.builder().username("trainer1").build()).build())
                .trainee(Trainee.builder().user(User.builder().username("trainee1").build()).build())
                .trainingType(TrainingType.builder().trainingTypeName("Yoga").build())
                .build());

        when(trainingRepository.getByTrainerCriteria(any(), any(), any(), any())).thenReturn(trainings);
        when(trainingMapper.convertToDto(any())).thenReturn(trainingDto);

        List<TrainingDto> result = trainingService.getTrainerTrainingsListCriteria("trainer1", fromDate, toDate, "trainee1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getTrainingType().getTrainingTypeName());
        assertEquals("trainee1", result.get(0).getTrainee().getUser().getUsername());

        verify(trainingRepository, times(1)).getByTrainerCriteria(any(), any(), any(), any());
    }
}