package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrainingDAOTest {

    @Autowired
    private TrainingRepository trainingDAO;

    private Training sampleTraining;

    @BeforeEach
    void setUp() {
/*        sampleTrainer = Trainer.builder()
                .firstName("Nazar")
                .lastName("Panasiuk")
                .username("nazar_panasiuk")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        sampleTrainer = Trainer.builder()
                .firstName("Nazar")
                .lastName("Panasiuk")
                .username("nazar_panasiuk")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();*/

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
    void testSaveTrainer() {
        Training savedTraining = trainingDAO.save(sampleTraining);

        assertNotNull(savedTraining);
        assertEquals("Name", savedTraining.getTrainingName());
        assertEquals(1L, savedTraining.getId());
    }

    @Test
    void testFindById() {
        Training savedTraining = trainingDAO.save(sampleTraining);

        Optional<Training> foundTraining = trainingDAO.findById(savedTraining.getId());

        assertTrue(foundTraining.isPresent());
        assertEquals(savedTraining.getTrainingName(), foundTraining.get().getTrainingName());
    }
}
