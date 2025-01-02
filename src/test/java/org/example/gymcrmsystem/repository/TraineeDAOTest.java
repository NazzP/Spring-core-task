package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.model.Trainee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TraineeDAOTest {

    private AnnotationConfigApplicationContext context;
    private TraineeRepository traineeRepository;
    private Trainee sampleTrainee;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        traineeRepository = context.getBean(TraineeRepository.class);

        sampleTrainee = Trainee.builder()
                .id(1L)
                .firstName("Firstname")
                .lastName("LastName")
                .password("password")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();
    }

    @Test
    void saveTraineeSuccess() {
        Trainee savedTrainee = traineeRepository.save(sampleTrainee);

        assertNotNull(savedTrainee);
        assertEquals("Firstname", savedTrainee.getFirstName());
        assertEquals(1L, savedTrainee.getId());
    }

    @Test
    void findByIdSuccess() {
        Trainee savedTrainee = traineeRepository.save(sampleTrainee);

        Optional<Trainee> foundTrainee = traineeRepository.findById(savedTrainee.getId());

        assertTrue(foundTrainee.isPresent());
        assertEquals(savedTrainee.getFirstName(), foundTrainee.get().getFirstName());
    }

    @Test
    void deleteByIdSuccess() {
        Trainee savedTrainee = traineeRepository.save(sampleTrainee);
        Optional<Trainee> foundTrainee = traineeRepository.findById(savedTrainee.getId());
        assertTrue(foundTrainee.isPresent());

        traineeRepository.deleteById(savedTrainee.getId());

        foundTrainee = traineeRepository.findById(savedTrainee.getId());
        assertFalse(foundTrainee.isPresent());
    }

    @AfterEach
    void tearDown() {
        context.close();
    }
}