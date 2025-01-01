package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.impl.TraineeServiceImpl;
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
    private TraineeRepository traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Spy
    private TraineeMapper traineeMapper;

    private Trainee sampleTrainee;
    private TraineeDto sampleTraineeDTO;

    @BeforeEach
    public void setUp() {
        sampleTrainee = Trainee.builder()
                .id(1L)
                .firstName("Nazar")
                .lastName("Panasiuk")
                .username("nazar_panasiuk")
                .password("password")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();

        sampleTraineeDTO = traineeMapper.convertToDto(sampleTrainee);
    }

    @Test
    void testCreateTrainee_Success() {
        when(traineeDAO.save(any(Trainee.class))).thenReturn(sampleTrainee);

        TraineeDto createdTrainee = traineeService.create(sampleTraineeDTO);

        assertNotNull(createdTrainee);
        assertEquals("Nazar", createdTrainee.getFirstName());
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testCreateTrainee_NullInput() {
        assertThrows(NullEntityReferenceException.class, () -> traineeService.create(null));
    }

    @Test
    void testSelectTrainee_Success() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.of(sampleTrainee));

        TraineeDto foundTrainee = traineeService.select(1L);

        assertNotNull(foundTrainee);
        assertEquals("Nazar", foundTrainee.getFirstName());
        verify(traineeDAO, times(1)).findById(1L);
    }

    @Test
    void testSelectTrainee_NotFound() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.select(1L));
    }

    @Test
    void testUpdateTrainee_Success() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.of(sampleTrainee));
        when(traineeDAO.save(any(Trainee.class))).thenReturn(sampleTrainee);

        TraineeDto updatedTrainee = traineeService.update(1L, sampleTraineeDTO);

        assertNotNull(updatedTrainee);
        assertEquals("Nazar", updatedTrainee.getFirstName());
        verify(traineeDAO, times(1)).findById(1L);
        verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee_NotFound() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.update(1L, sampleTraineeDTO));
    }

    @Test
    void testDeleteTrainee_Success() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.of(sampleTrainee));

        traineeService.delete(1L);

        verify(traineeDAO, times(1)).findById(1L);
        verify(traineeDAO, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTrainee_NotFound() {
        when(traineeDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.delete(1L));
    }
}