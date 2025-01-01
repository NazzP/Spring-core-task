package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.*;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.model.Training;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Spy
    private TraineeMapper traineeMapper;
    @Spy
    private TrainerMapper trainerMapper;
    @Spy
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private TrainingDto trainingDTO;
    private Training training;

    @BeforeEach
    void setUp() {
        TraineeDto traineeDTO = TraineeDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username("johndoe")
                        .password("password")
                        .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Street")
                .build();

        TrainerDto trainerDTO = TrainerDto.builder()
                        .firstName("Jane")
                        .lastName("Smith")
                        .username("janesmith")
                        .password("password")
                        .isActive(true)
                .specialization(TrainingType.builder()
                        .trainingTypeName("Fitness")
                        .build())
                .build();

        traineeRepository.save(traineeMapper.convertToEntity(traineeDTO));
        trainerRepository.save(trainerMapper.convertToEntity(trainerDTO));

        trainingDTO = TrainingDto.builder()
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Cardio")
                .trainingType(TrainingType.builder().trainingTypeName("Cardio").build())
                .date(new Date())
                .duration(60)
                .build();

        training = trainingMapper.convertToEntity(trainingDTO);
    }

    @Test
    void create_ShouldSaveTrainingAndReturnDTO() {
        when(trainingRepository.save(any(Training.class))).thenReturn(training);

        TrainingDto result = trainingService.create(trainingDTO);

        assertNotNull(result);
        assertEquals("Morning Cardio", result.getTrainingName());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void create_ShouldThrowExceptionWhenDTOIsNull() {
        assertThrows(NullEntityReferenceException.class, () -> trainingService.create(null));
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void select_ShouldReturnTrainingDTOWhenIdExists() {
        Long trainingId = 1L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));

        TrainingDto result = trainingService.select(trainingId);

        assertNotNull(result);
        assertEquals("Morning Cardio", result.getTrainingName());
        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void select_ShouldThrowExceptionWhenIdDoesNotExist() {
        Long trainingId = 1L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainingService.select(trainingId));
        verify(trainingRepository, times(1)).findById(trainingId);
    }
}
