package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository trainerRepository;

    private Trainer sampleTrainer;

    @BeforeEach
    void setUpEach() {
        sampleTrainer = Trainer.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("Firstname.LastName")
                        .password("password")
                        .isActive(true)
                        .build())
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();
    }

    @Test
    void SaveTrainerSuccess() {
        Trainer savedTrainer = trainerRepository.save(sampleTrainer);

        assertNotNull(savedTrainer);
        assertEquals("FirstName", savedTrainer.getUser().getFirstName());
    }

    @Test
    void SaveTrainerWithExistingId() {
        sampleTrainer.setId(1L);
        Trainer savedTrainer = trainerRepository.save(sampleTrainer);

        assertNotNull(savedTrainer);
        assertEquals(1L, savedTrainer.getId());
    }


    @Test
    void FindByUsernameSuccess() {
        Trainer savedTrainer = trainerRepository.save(sampleTrainer);

        Optional<Trainer> foundTrainer = trainerRepository.findByUsername(savedTrainer.getUser().getUsername());

        assertTrue(foundTrainer.isPresent());
        assertEquals(savedTrainer.getUser().getFirstName(), foundTrainer.get().getUser().getFirstName());
    }

    @Test
    void findByUsernameNoResult() {
        Optional<Trainer> foundTrainer = trainerRepository.findByUsername("NonExistent.Username");

        assertFalse(foundTrainer.isPresent());
    }

    @Test
    void FindAllSuccess() {
        Trainer secondTrainer = Trainer.builder()
                .user(User.builder()
                        .firstName("Second")
                        .lastName("Trainer")
                        .username("Second.Trainer")
                        .password("password")
                        .isActive(true)
                        .build())
                .specialization(TrainingType.builder().id(2L).trainingTypeName("Strength Training").build())
                .build();

        Trainer savedTrainer1 = trainerRepository.save(sampleTrainer);
        Trainer savedTrainer2 = trainerRepository.save(secondTrainer);

        List<Trainer> trainers = trainerRepository.findAll();

        assertFalse(trainers.isEmpty());
        assertEquals(savedTrainer1.getUser().getFirstName(), trainers.get(0).getUser().getFirstName());
        assertEquals(savedTrainer2.getUser().getFirstName(), trainers.get(1).getUser().getFirstName());
    }

    @Test
    void findAllWhenNoTrainersExist() {
        List<Trainer> trainers = trainerRepository.findAll();
        assertTrue(trainers.isEmpty());
    }

}
