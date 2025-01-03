package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.example.gymcrmsystem.dto.*;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
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

    @Spy
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training sampleTraining;
    private TrainingDto sampleTrainingDto;


    @BeforeEach
    void setUp() {
        sampleTrainingDto = TrainingDto.builder()
                .id(4L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Morning Cardio")
                .trainingType(TrainingType.builder().trainingTypeName("Cardio").build())
                .date(new Date())
                .duration(60)
                .build();

        sampleTraining = trainingMapper.convertToEntity(sampleTrainingDto);
    }

    @Test
    void createTrainingSuccess() {
        when(trainingRepository.save(any(Training.class))).thenReturn(sampleTraining);

        TrainingDto result = trainingService.create(sampleTrainingDto);

        assertNotNull(result);
        assertEquals("Morning Cardio", result.getTrainingName());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void createTrainingNullInput() {
        assertThrows(NullEntityReferenceException.class, () -> trainingService.create(null));
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void createTrainingAlreadyExists() {
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(sampleTraining));
        assertThrows(EntityAlreadyExistsException.class, () -> trainingService.create(sampleTrainingDto));
    }

    @Test
    void selectTrainingSuccess() {
        Long trainingId = 4L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(sampleTraining));

        TrainingDto result = trainingService.select(trainingId);

        assertNotNull(result);
        assertEquals("Morning Cardio", result.getTrainingName());
        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void selectTrainingNotFound() {
        Long trainingId = 1L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainingService.select(trainingId));
        verify(trainingRepository, times(1)).findById(trainingId);
    }
}