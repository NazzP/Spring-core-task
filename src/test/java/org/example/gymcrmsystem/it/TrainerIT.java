package org.example.gymcrmsystem.it;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.service.impl.TrainerServiceImpl;
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
class TrainerIT {

    @Autowired
    private TrainerServiceImpl trainerService;

    @Autowired
    private Storage<Trainer> trainerStorage;

    private TrainerDto trainerDto;

    @BeforeEach
    public void setup() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("Yoga");

        trainerDto = new TrainerDto();
        trainerDto.setId(1L);
        trainerDto.setFirstName("FirstName");
        trainerDto.setLastName("LastName");
        trainerDto.setSpecialization(trainingType);
    }

    @AfterEach
    public void clear() {
        trainerStorage.remove(1L);
    }

    @Test
    void createTrainer_success() {
        TrainerDto result = trainerService.create(trainerDto);

        assertNotNull(result);
        assertEquals("FirstName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals("Yoga", result.getSpecialization().getTrainingTypeName());

        Trainer savedTrainer = trainerStorage.get(1L);
        assertNotNull(savedTrainer);
        assertEquals("FirstName", savedTrainer.getFirstName());
    }

    @Test
    void createTrainer_entityAlreadyExists() {
        trainerService.create(trainerDto);

        assertThrows(EntityAlreadyExistsException.class, () -> trainerService.create(trainerDto));
    }

    @Test
    void selectTrainer_success() {
        trainerService.create(trainerDto);

        TrainerDto result = trainerService.select(1L);

        assertNotNull(result);
        assertEquals("FirstName", result.getFirstName());
        assertEquals("LastName", result.getLastName());
        assertEquals("Yoga", result.getSpecialization().getTrainingTypeName());

        Trainer retrievedTrainer = trainerStorage.get(1L);
        assertNotNull(retrievedTrainer);
        assertEquals("FirstName", retrievedTrainer.getFirstName());
    }

    @Test
    void selectTrainer_notFound() {
        assertThrows(EntityNotFoundException.class, () -> trainerService.select(1L));
    }

    @Test
    void updateTrainer_success() {
        trainerService.create(trainerDto);

        TrainerDto updatedDto = new TrainerDto();
        updatedDto.setFirstName("Updated");
        updatedDto.setLastName("UserName");
        updatedDto.setId(1L);

        TrainingType pilatesType = new TrainingType();
        pilatesType.setId(2L);
        pilatesType.setTrainingTypeName("Pilates");

        updatedDto.setSpecialization(pilatesType);

        TrainerDto updatedResult = trainerService.update(1L, updatedDto);

        assertNotNull(updatedResult);
        assertEquals("Updated", updatedResult.getFirstName());
        assertEquals("UserName", updatedResult.getLastName());
        assertEquals("Pilates", updatedResult.getSpecialization().getTrainingTypeName());

        Trainer updatedTrainer = trainerStorage.get(1L);
        assertNotNull(updatedTrainer);
        assertEquals("Updated", updatedTrainer.getFirstName());
        assertEquals("UserName", updatedTrainer.getLastName());
        assertEquals("Pilates", updatedTrainer.getSpecialization().getTrainingTypeName());
    }

    @Test
    void updateTrainer_notFound() {
        TrainerDto updatedDto = new TrainerDto();
        updatedDto.setFirstName("Updated");
        updatedDto.setLastName("UserName");

        TrainingType pilatesType = new TrainingType();
        pilatesType.setId(2L);
        pilatesType.setTrainingTypeName("Pilates");

        updatedDto.setSpecialization(pilatesType);

        assertThrows(EntityNotFoundException.class, () -> trainerService.update(1L, updatedDto));
    }
}