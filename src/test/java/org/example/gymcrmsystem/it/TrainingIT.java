package org.example.gymcrmsystem.it;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.EntityAlreadyExistsException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.model.Training;
import org.example.gymcrmsystem.service.impl.TrainingServiceImpl;
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
class TrainingIT {

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private Storage<Training> trainingStorage;

    private TrainingDto trainingDto;

    @BeforeEach
    public void setup() {
        trainingDto = new TrainingDto();
        trainingDto.setId(4L);
        trainingDto.setTrainingName("Yoga Training");
        trainingDto.setDuration(60);
    }

    @AfterEach
    public void clear() {
        trainingStorage.remove(4L);
    }

    @Test
    void createTraining_success() {
        TrainingDto result = trainingService.create(trainingDto);

        assertNotNull(result);
        assertEquals("Yoga Training", result.getTrainingName());
        assertEquals(60, result.getDuration());

        Training savedTraining = trainingStorage.get(4L);
        assertNotNull(savedTraining);
        assertEquals("Yoga Training", savedTraining.getTrainingName());
    }

    @Test
    void createTraining_entityAlreadyExists() {
        trainingService.create(trainingDto);

        assertThrows(EntityAlreadyExistsException.class, () -> trainingService.create(trainingDto));
    }

    @Test
    void selectTraining_success() {
        trainingService.create(trainingDto);

        TrainingDto result = trainingService.select(4L);

        assertNotNull(result);
        assertEquals("Yoga Training", result.getTrainingName());
        assertEquals(60, result.getDuration());

        Training retrievedTraining = trainingStorage.get(4L);
        assertNotNull(retrievedTraining);
        assertEquals("Yoga Training", retrievedTraining.getTrainingName());
    }

    @Test
    void selectTraining_notFound() {
        assertThrows(EntityNotFoundException.class, () -> trainingService.select(5L));
    }
}
