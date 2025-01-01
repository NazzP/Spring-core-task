package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Spy
    private TrainerMapper trainerMapper;

    private Trainer trainer;
    private TrainerDto trainerDto;

    @BeforeEach
    void setUp() {
        trainer = Trainer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        trainerDto = trainerMapper.convertToDto(trainer);
    }

    @Test
    void testCreateTrainer_Success() {
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDto result = trainerService.create(trainerDto);

        assertNotNull(result);
        assertEquals(trainerDto.getFirstName(), result.getFirstName());
        assertEquals(trainerDto.getSpecialization().getTrainingTypeName(), result.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testCreateTrainer_NullInput() {
        assertThrows(NullEntityReferenceException.class, () -> trainerService.create(null));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    void testSelectTrainer_Success() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        TrainerDto result = trainerService.select(1L);

        assertNotNull(result);
        assertEquals(trainerDto.getFirstName(), result.getFirstName());
        assertEquals(trainerDto.getSpecialization().getTrainingTypeName(), result.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void testSelectTrainer_NotFound() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.select(1L));

        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTrainer_Success() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDto updatedDTO = TrainerDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .password("password123")
                .isActive(false)
                .specialization(TrainingType.builder().trainingTypeName("Pilates").build())
                .build();

        TrainerDto result = trainerService.update(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Pilates", result.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).findById(1L);
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer_NotFound() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        TrainerDto updatedDTO = TrainerDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .password("password123")
                .isActive(false)
                .specialization(TrainingType.builder().trainingTypeName("Pilates").build())
                .build();

        assertThrows(EntityNotFoundException.class, () -> trainerService.update(1L, updatedDTO));

        verify(trainerRepository, times(1)).findById(1L);
        verify(trainerRepository, never()).save(any(Trainer.class));
    }
}