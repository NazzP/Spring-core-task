package org.example.gymcrmsystem.it;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.*;
import org.example.gymcrmsystem.entity.*;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainingMapper;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("prod")
class TrainingServiceIT {

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    private TrainingMapper trainingMapper;

    private Trainee trainee;
    private Trainer trainer;
    private Training training;

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("datasource.url", postgres::getJdbcUrl);
        registry.add("datasource.username", postgres::getUsername);
        registry.add("datasource.password", postgres::getPassword);
        registry.add("hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQL10Dialect");
        registry.add("hibernate.hbm2ddl.auto", () -> "create");
        registry.add("hibernate.show_sql", () -> true);
        registry.add("hibernate.format_sql", () -> true);
        registry.add("hibernate.jdbc.lob.non_contextual_creation", () -> true);
    }

    @BeforeEach
    void setup() {

        TrainingType yogaType = trainingTypeRepository.findByName("Yoga")
                .orElseThrow(() -> new EntityNotFoundException("TrainingType Yoga not found"));

        User trainerUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("pass")
                .isActive(true)
                .build();

        User traineeUser = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .username("Jane.Smith")
                .password("pass")
                .isActive(true)
                .build();

        trainer = Trainer.builder()
                .user(trainerUser)
                .specialization(yogaType)
                .build();

        trainee = Trainee.builder()
                .user(traineeUser)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .address("123 Address St")
                .build();

        Trainee existingTrainee = traineeRepository.save(trainee);
        Trainer existingTrainer = trainerRepository.save(trainer);

        training = Training.builder()
                .trainingType(yogaType)
                .trainer(existingTrainer)
                .trainee(existingTrainee)
                .trainingName("Morning Yoga")
                .date(LocalDate.now())
                .duration(60)
                .build();
    }

    @Test
    void addTrainingSuccess() {
        TrainingDto addedTraining = trainingService.add(trainingMapper.convertToDto(training));

        assertNotNull(addedTraining);
        assertEquals("Yoga", addedTraining.getTrainingType().getTrainingTypeName());
        assertEquals("John", addedTraining.getTrainer().getUser().getFirstName());
        assertEquals("Jane", addedTraining.getTrainee().getUser().getFirstName());
    }

    @Test
    void addTrainingTrainingTypeNotFound() {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainingType(new TrainingTypeDto("NonExistingType"))
                .trainer(TrainerDto.builder()
                        .user(new UserDto("John", "Doe", "trainer1", "password", true))
                        .specialization(new TrainingTypeDto("Yoga"))
                        .build())
                .trainee(TraineeDto.builder()
                        .user(new UserDto("Jane", "Smith", "trainee1", "password", true))
                        .dateOfBirth(LocalDate.of(1990, 5, 15))
                        .address("123 Address St")
                        .build())
                .trainingName("Morning Yoga")
                .date(LocalDate.now())
                .duration(60)
                .build();

        assertThrows(EntityNotFoundException.class, () -> trainingService.add(trainingDto),
                "TrainingType with name NonExistingType wasn't found");
    }

    @Test
    void getTraineeTrainingsListCriteriaSuccess() {
        trainingService.add(trainingMapper.convertToDto(training));
        String traineeUsername = trainee.getUser().getUsername();
        LocalDate fromDate = training.getDate().minusDays(10);
        LocalDate toDate = training.getDate();
        String trainerName = trainer.getUser().getFirstName();
        String trainingType = "Yoga";

        List<TrainingDto> trainings = trainingService.getTraineeTrainingsListCriteria(traineeUsername, fromDate, toDate, trainerName, trainingType);

        assertNotNull(trainings);
        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals("Yoga", trainings.get(0).getTrainingType().getTrainingTypeName());
    }

    @Test
    void getTraineeTrainingsListCriteriaNoResults() {
        String traineeUsername = "trainee1";
        LocalDate fromDate = LocalDate.now().minusDays(10);
        LocalDate toDate = LocalDate.now();
        String trainerName = "Jane";
        String trainingType = "Yoga";

        assertThrows(EntityNotFoundException.class, () -> trainingService.getTraineeTrainingsListCriteria(traineeUsername, fromDate, toDate, trainerName, trainingType),
                "Trainee with username " + traineeUsername + "wasn't found");
    }

    @Test
    void getTrainerTrainingsListCriteriaSuccess() {
        String trainerUsername = trainer.getUser().getUsername();
        LocalDate fromDate = training.getDate().minusDays(10);
        LocalDate toDate = training.getDate();
        String traineeName = trainee.getUser().getFirstName();

        trainingService.add(trainingMapper.convertToDto(training));

        List<TrainingDto> trainings = trainingService.getTrainerTrainingsListCriteria(trainerUsername, fromDate, toDate, traineeName);

        Assertions.assertNotNull(trainings);
        Assertions.assertFalse(trainings.isEmpty());
        Assertions.assertEquals(1, trainings.size());
        Assertions.assertEquals("Jane", trainings.get(0).getTrainee().getUser().getFirstName());
    }

    @Test
    void getTrainerTrainingsListCriteriaNoResults() {
        String trainerUsername = "trainer1";
        LocalDate fromDate = LocalDate.now().minusDays(10);
        LocalDate toDate = LocalDate.now();
        String traineeName = "nonexistentTrainee";

        assertThrows(EntityNotFoundException.class, () -> trainingService.getTrainerTrainingsListCriteria(trainerUsername, fromDate, toDate, traineeName),
                "Trainer with username " + trainerUsername + "wasn't found");
    }
}
