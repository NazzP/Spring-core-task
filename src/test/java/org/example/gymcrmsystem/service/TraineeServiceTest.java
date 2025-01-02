package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.impl.TraineeServiceImpl;
import org.example.gymcrmsystem.utils.UsernameGenerator;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Spy
    private TraineeMapper traineeMapper;

    private Trainee sampleTrainee;
    private TraineeDto sampleTraineeDto;

    @BeforeEach
    public void setUp() {
        sampleTrainee = Trainee.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .password("password")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();

        sampleTraineeDto = traineeMapper.convertToDto(sampleTrainee);
    }

    @Test
    void createTraineeSuccess() {
        when(usernameGenerator.generateUniqueUsername(any(TraineeDto.class))).thenReturn("FirstName.LastName");

        Trainee traineeToSave = new Trainee();
        traineeToSave.setFirstName("FirstName");
        traineeToSave.setLastName("LastName");
        traineeToSave.setUsername("FirstName.LastName");

        when(traineeRepository.save(any(Trainee.class))).thenReturn(traineeToSave);

        TraineeDto createdTrainee = traineeService.create(sampleTraineeDto);

        assertNotNull(createdTrainee);
        assertEquals("FirstName", createdTrainee.getFirstName());
        assertEquals("FirstName.LastName", createdTrainee.getUsername());

        verify(usernameGenerator, times(1)).generateUniqueUsername(any(TraineeDto.class));
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }


    @Test
    void createTraineeNullInput() {
        assertThrows(NullEntityReferenceException.class, () -> traineeService.create(null));
    }

    @Test
    void createTraineeAlreadyExists() {
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(sampleTrainee));
        assertThrows(EntityAlreadyExistsException.class, () -> traineeService.create(sampleTraineeDto));
    }

    @Test
    void selectTraineeSuccess() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(sampleTrainee));

        TraineeDto foundTrainee = traineeService.select(1L);

        assertNotNull(foundTrainee);
        assertEquals("FirstName", foundTrainee.getFirstName());
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    void selectTraineeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.select(1L));
    }

    @Test
    void updateTraineeSuccess() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(sampleTrainee));
        when(usernameGenerator.generateUniqueUsername(any(TraineeDto.class))).thenReturn("FirstName.LastName");

        TraineeDto traineeDto = TraineeDto.builder()
                .firstName("Updated")
                .lastName("Username")
                .build();

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(1L);
        updatedTrainee.setFirstName("Updated");
        updatedTrainee.setLastName("Username");
        updatedTrainee.setUsername("Updated.Username");

        when(traineeRepository.save(any(Trainee.class))).thenReturn(updatedTrainee);

        TraineeDto result = traineeService.update(1L, traineeDto);

        assertNotNull(result);
        assertEquals("Updated", result.getFirstName());
        assertEquals("Username", result.getLastName());
        assertEquals("Updated.Username", result.getUsername());

        verify(usernameGenerator, times(1)).generateUniqueUsername(any(TraineeDto.class));
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void updateTraineeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.update(1L, sampleTraineeDto));
    }

    @Test
    void deleteTraineeSuccess() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(sampleTrainee));

        traineeService.delete(1L);

        verify(traineeRepository, times(1)).findById(1L);
        verify(traineeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTraineeNotFound() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.delete(1L));
    }
}