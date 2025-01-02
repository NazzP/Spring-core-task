package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrainerDAOTest {

    private AnnotationConfigApplicationContext context;
    private TrainerRepository trainerRepository;
    private Trainer sampleTrainer;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        trainerRepository = context.getBean(TrainerRepository.class);

        sampleTrainer = Trainer.builder()
                .id(1L)
                .firstName("Firstname")
                .lastName("LastName")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();
    }

    @Test
    void SaveTrainerSuccess() {
        Trainer savedTrainer = trainerRepository.save(sampleTrainer);

        assertNotNull(savedTrainer);
        assertEquals("Firstname", savedTrainer.getFirstName());
        assertEquals(1L, savedTrainer.getId());
    }

    @Test
    void FindByIdSuccess() {
        Trainer savedTrainer = trainerRepository.save(sampleTrainer);

        Optional<Trainer> foundTrainer = trainerRepository.findById(savedTrainer.getId());

        assertTrue(foundTrainer.isPresent());
        assertEquals(savedTrainer.getFirstName(), foundTrainer.get().getFirstName());
    }

    @AfterEach
    void tearDown() {
        context.close();
    }
}