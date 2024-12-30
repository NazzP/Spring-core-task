package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrainerDAOTest {

    @Autowired
    private TrainerRepository trainerDAO;

    private Trainer sampleTrainer;

    @BeforeEach
    void setUp() {
        sampleTrainer = Trainer.builder()
                .firstName("Nazar")
                .lastName("Panasiuk")
                .username("nazar_panasiuk")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();
    }

    @Test
    void testSaveTrainer() {
        Trainer savedTrainer = trainerDAO.save(sampleTrainer);

        assertNotNull(savedTrainer);
        assertEquals("Nazar", savedTrainer.getFirstName());
        assertEquals(1L, savedTrainer.getId());
    }

    @Test
    void testFindById() {
        Trainer savedTrainer = trainerDAO.save(sampleTrainer);

        Optional<Trainer> foundTrainee = trainerDAO.findById(savedTrainer.getId());

        assertTrue(foundTrainee.isPresent());
        assertEquals(savedTrainer.getFirstName(), foundTrainee.get().getFirstName());
    }
}
