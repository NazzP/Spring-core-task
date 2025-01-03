package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.service.impl.TrainerServiceImpl;
import org.example.gymcrmsystem.utils.UsernameGenerator;
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

    @Mock
    private UsernameGenerator usernameGenerator;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Spy
    private TrainerMapper trainerMapper;

    private Trainer sampleTrainer;
    private TrainerDto sampleTrainerDto;

    @BeforeEach
    void setUp() {
        sampleTrainer = Trainer.builder()
                .id(1L)
                .firstName("Firstname")
                .lastName("LastName")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        sampleTrainerDto = trainerMapper.convertToDto(sampleTrainer);
    }

    @Test
    void createTrainerSuccess() {
        when(usernameGenerator.generateUniqueUsername(any(TrainerDto.class))).thenReturn("FirstName.LastName");

        Trainer trainerToSave = new Trainer();
        trainerToSave.setFirstName("FirstName");
        trainerToSave.setLastName("LastName");
        trainerToSave.setUsername("FirstName.LastName");
        trainerToSave.setSpecialization(TrainingType.builder().trainingTypeName("Yoga").build());

        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainerToSave);

        TrainerDto createdTrainer = trainerService.create(sampleTrainerDto);

        assertNotNull(createdTrainer);
        assertEquals("FirstName", createdTrainer.getFirstName());
        assertEquals("FirstName.LastName", createdTrainer.getUsername());
        assertEquals("Yoga", createdTrainer.getSpecialization().getTrainingTypeName());

        verify(usernameGenerator, times(1)).generateUniqueUsername(any(TrainerDto.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void createTrainerNullInput() {
        assertThrows(NullEntityReferenceException.class, () -> trainerService.create(null));
        verify(trainerRepository, never()).save(any(Trainer.class));
    }

    @Test
    void createTraineeAlreadyExists() {
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(sampleTrainer));
        assertThrows(EntityAlreadyExistsException.class, () -> trainerService.create(sampleTrainerDto));
    }

    @Test
    void selectTrainerSuccess() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(sampleTrainer));

        TrainerDto result = trainerService.select(1L);

        assertNotNull(result);
        assertEquals(sampleTrainerDto.getFirstName(), result.getFirstName());
        assertEquals(sampleTrainerDto.getSpecialization().getTrainingTypeName(), result.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void selectTrainerNotFound() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.select(1L));

        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void updateTrainerSuccess() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(sampleTrainer));
        when(usernameGenerator.generateUniqueUsername(any(TrainerDto.class))).thenReturn("Updated.Username");

        TrainerDto updatedDTO = TrainerDto.builder()
                .firstName("Updated")
                .lastName("Username")
                .build();

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(1L);
        updatedTrainer.setFirstName("Updated");
        updatedTrainer.setLastName("Username");
        updatedTrainer.setUsername("Updated.Username");
        updatedTrainer.setSpecialization(sampleTrainer.getSpecialization());

        when(trainerRepository.save(any(Trainer.class))).thenReturn(updatedTrainer);

        TrainerDto result = trainerService.update(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("Username", result.getLastName());
        assertEquals("Updated.Username", result.getUsername());

        verify(usernameGenerator, times(1)).generateUniqueUsername(any(TrainerDto.class));
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }



    @Test
    void updateTrainerNotFound() {
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