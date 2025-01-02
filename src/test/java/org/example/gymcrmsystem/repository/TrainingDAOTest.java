package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrainingDAOTest {

    private AnnotationConfigApplicationContext context;
    private TrainingRepository trainingRepository;
    private Training sampleTraining;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        trainingRepository = context.getBean(TrainingRepository.class);

        sampleTraining = Training.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Name")
                .date(new Date())
                .duration(1)
                .build();
    }

    @Test
    void saveTrainerSuccess() {
        Training savedTraining = trainingRepository.save(sampleTraining);

        assertNotNull(savedTraining);
        assertEquals("Name", savedTraining.getTrainingName());
        assertEquals(1L, savedTraining.getId());
    }

    @Test
    void findByIdSuccess() {
        Training savedTraining = trainingRepository.save(sampleTraining);

        Optional<Training> foundTraining = trainingRepository.findById(savedTraining.getId());

        assertTrue(foundTraining.isPresent());
        assertEquals(savedTraining.getTrainingName(), foundTraining.get().getTrainingName());
    }

    @AfterEach
    void tearDown() {
        context.close();
    }
}