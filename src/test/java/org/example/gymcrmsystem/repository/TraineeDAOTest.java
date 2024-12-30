package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TraineeDAOTest {

    @Autowired
    private TraineeRepository traineeDAO;

    private Trainee sampleTrainee;

    @BeforeEach
    void setUp() {
        sampleTrainee = Trainee.builder()
                .firstName("Nazar")
                .lastName("Panasiuk")
                .username("nazar_panasiuk")
                .password("password")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();
    }


    @Test
    void testSaveTrainee() {
        Trainee savedTrainee = traineeDAO.save(sampleTrainee);

        assertNotNull(savedTrainee);
        assertEquals("Nazar", savedTrainee.getFirstName());
        assertEquals(1L, savedTrainee.getId());
    }

    @Test
    void testFindById() {
        Trainee savedTrainee = traineeDAO.save(sampleTrainee);

        Optional<Trainee> foundTrainee = traineeDAO.findById(savedTrainee.getId());

        assertTrue(foundTrainee.isPresent());
        assertEquals(savedTrainee.getFirstName(), foundTrainee.get().getFirstName());
    }

    @Test
    void testDeleteById() {
        Trainee savedTrainee = traineeDAO.save(sampleTrainee);
        Optional<Trainee> foundTrainee = traineeDAO.findById(savedTrainee.getId());
        assertTrue(foundTrainee.isPresent());

        traineeDAO.deleteById(savedTrainee.getId());

        foundTrainee = traineeDAO.findById(savedTrainee.getId());
        assertFalse(foundTrainee.isPresent());
    }
}
