package org.example.gymcrmsystem.it;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.service.impl.TraineeServiceImpl;
import org.example.gymcrmsystem.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class TraineeIT {

    @Autowired
    private TraineeServiceImpl traineeService;

    @Autowired
    private Storage<Trainee> traineeStorage;

    private TraineeDto traineeDto;

    @BeforeEach
    public void setup() {
        traineeDto = new TraineeDto();
        traineeDto.setId(4L);
        traineeDto.setFirstName("FirstName");
        traineeDto.setLastName("LastName");
    }

    @AfterEach
    public void cleat() {
        traineeStorage.remove(4L);
    }

    @Test
    void createTrainee_success() {
        TraineeDto result = traineeService.create(traineeDto);

        assertNotNull(result);
        assertEquals("FirstName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals("FirstName.LastName", result.getUsername());

        Trainee savedTrainee = traineeStorage.get(4L);
        assertNotNull(savedTrainee);
        assertEquals("FirstName", savedTrainee.getFirstName());
    }

    @Test
    void createTrainee_entityAlreadyExists() {
        traineeService.create(traineeDto);

        assertThrows(EntityAlreadyExistsException.class, () -> traineeService.create(traineeDto));
    }

    @Test
    void selectTrainee_success() {
        traineeService.create(traineeDto);

        TraineeDto result = traineeService.select(4L);

        assertNotNull(result);
        assertEquals("FirstName", result.getFirstName());
        assertEquals("LastName", result.getLastName());

        Trainee retrievedTrainee = traineeStorage.get(4L);
        assertNotNull(retrievedTrainee);
        assertEquals("FirstName", retrievedTrainee.getFirstName());
    }

    @Test
    void selectTrainee_notFound() {
        assertThrows(EntityNotFoundException.class, () -> traineeService.select(4L));
    }

    @Test
    void updateTrainee_success() {
        traineeService.create(traineeDto);

        TraineeDto updatedDto = new TraineeDto();
        updatedDto.setFirstName("Updated");
        updatedDto.setLastName("UserName");
        updatedDto.setId(4L);

        TraineeDto updatedResult = traineeService.update(4L, updatedDto);

        assertNotNull(updatedResult);
        assertEquals("Updated", updatedResult.getFirstName());
        assertEquals("UserName", updatedResult.getLastName());

        Trainee updatedTrainee = traineeStorage.get(4L);
        assertNotNull(updatedTrainee);
        assertEquals("Updated", updatedTrainee.getFirstName());
        assertEquals("UserName", updatedTrainee.getLastName());
    }

    @Test
    void updateTrainee_notFound() {
        TraineeDto updatedDto = new TraineeDto();
        updatedDto.setFirstName("Updated");
        updatedDto.setLastName("UserName");

        assertThrows(EntityNotFoundException.class, () -> traineeService.update(9L, updatedDto));
    }

    @Test
    void deleteTrainee_success() {
        traineeService.create(traineeDto);

        traineeService.delete(4L);

        Trainee deletedTrainee = traineeStorage.get(4L);
        assertNull(deletedTrainee);
    }

    @Test
    void deleteTrainee_notFound() {
        assertThrows(EntityNotFoundException.class, () -> traineeService.delete(4L));
    }
}

