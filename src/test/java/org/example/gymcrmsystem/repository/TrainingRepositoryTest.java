package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TrainingTypeDto;
import org.example.gymcrmsystem.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional(propagation = Propagation.REQUIRED)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TrainingRepositoryTest {

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    private Trainee sampleTrainee;
    private Trainer sampleTrainer;
    private Training sampleTraining;

    @BeforeEach
    void setUpEach() {
        sampleTrainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("FirstName.LastName")
                        .password("password")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Main St")
                .build();

        sampleTrainer = Trainer.builder()
                .user(User.builder()
                        .firstName("Trainer")
                        .lastName("Name")
                        .username("Trainer.Name")
                        .password("password")
                        .isActive(true)
                        .build())
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        sampleTraining = Training.builder()
                .trainee(sampleTrainee)
                .trainer(sampleTrainer)
                .trainingType(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .trainingName("Name")
                .date(LocalDate.of(2030, 1, 1))
                .duration(1)
                .build();

        traineeRepository.save(sampleTrainee);
        trainerRepository.save(sampleTrainer);
    }

    @Test
    void saveTrainingSuccess() {
        Training savedTraining = trainingRepository.save(sampleTraining);

        assertNotNull(savedTraining);
        assertEquals("Name", savedTraining.getTrainingName());
        assertEquals(sampleTrainee.getUser().getUsername(), savedTraining.getTrainee().getUser().getUsername());
        assertEquals(sampleTrainer.getUser().getUsername(), savedTraining.getTrainer().getUser().getUsername());
    }

    @Test
    void saveTrainingWithExistingId() {
        sampleTraining.setId(1L);
        Training savedTraining = trainingRepository.save(sampleTraining);

        assertNotNull(savedTraining);
        assertEquals(1L, savedTraining.getId());
    }

    @Test
    void getByTraineeCriteriaSuccess() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2040, 1, 1);
        String trainerName = sampleTrainer.getUser().getFirstName();
        TrainingType trainingType = TrainingType.builder().id(1L).trainingTypeName("Yoga").build();

        List<Training> trainings = trainingRepository.getByTraineeCriteria(
                sampleTrainee.getUser().getUsername(), fromDate, toDate, trainerName, trainingType.getTrainingTypeName());

        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals("FirstName.LastName", trainings.get(0).getTrainee().getUser().getUsername());
        assertEquals("Yoga", trainings.get(0).getTrainingType().getTrainingTypeName());
    }

    @Test
    void getByTraineeCriteriaEmpty() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2040, 1, 1);
        String differentTrainerName = "";

        List<Training> trainings = trainingRepository.getByTraineeCriteria(
                sampleTrainee.getUser().getUsername(), fromDate, toDate, differentTrainerName, "Yoga");

        assertTrue(trainings.isEmpty());
    }

    @Test
    void getByTraineeCriteriaNoResult() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2040, 1, 1);
        String trainerName = sampleTrainer.getUser().getFirstName();
        TrainingTypeDto trainingType = new TrainingTypeDto("Yoga");

        List<Training> trainings = trainingRepository.getByTraineeCriteria(
                "NonExistentUsername", fromDate, toDate, trainerName, trainingType.getTrainingTypeName());

        assertTrue(trainings.isEmpty());
    }

    @Test
    void getByTrainerCriteriaSuccess() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2040, 1, 1);
        String traineeName = sampleTrainee.getUser().getFirstName();

        List<Training> trainings = trainingRepository.getByTrainerCriteria(
                sampleTrainer.getUser().getUsername(), fromDate, toDate, traineeName);

        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals(sampleTrainer.getUser().getUsername(), trainings.get(0).getTrainer().getUser().getUsername());
        assertEquals("Yoga", trainings.get(0).getTrainingType().getTrainingTypeName());
    }

    @Test
    void getByTrainerCriteriaNoResult() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2026, 1, 1);
        String trainerName = sampleTrainer.getUser().getFirstName();

        List<Training> trainings = trainingRepository.getByTrainerCriteria(
                "NonExistentTrainer", fromDate, toDate, trainerName);

        assertTrue(trainings.isEmpty());
    }

    @Test
    void getByTrainerCriteriaEmpty() {
        trainingRepository.save(sampleTraining);

        LocalDate fromDate = LocalDate.of(2020, 1, 1);
        LocalDate toDate = LocalDate.of(2040, 1, 1);
        String invalidTraineeName = "";

        List<Training> trainings = trainingRepository.getByTrainerCriteria(
                sampleTrainer.getUser().getUsername(), fromDate, toDate, invalidTraineeName);

        assertTrue(trainings.isEmpty());
    }
}
