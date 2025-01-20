package org.example.gymcrmsystem.it;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.dto.TrainingTypeDto;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("prod")
class TrainerServiceIT {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainerRepository trainerRepository;

    private TrainerDto trainerDto;

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
        trainerDto = TrainerDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .isActive(true)
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();
    }

    @Test
    void createTrainerSuccess() {
        TrainerDto result = trainerService.create(trainerDto);

        assertNotNull(result);
        assertEquals("FirstName", result.getUser().getFirstName());
        assertEquals("LastName", result.getUser().getLastName());
        assertEquals("Yoga", result.getSpecialization().getTrainingTypeName());

        Trainer savedTrainer = trainerRepository.findByUsername(result.getUser().getUsername()).orElse(null);
        assertNotNull(savedTrainer);
        assertEquals(savedTrainer.getUser().getFirstName(), result.getUser().getFirstName());
        assertEquals(savedTrainer.getSpecialization().getTrainingTypeName(), result.getSpecialization().getTrainingTypeName());
    }

    @Test
    void selectTrainerSuccess() {
        TrainerDto result = trainerService.create(trainerDto);
        TrainerDto savedTrainerDto = trainerService.select(result.getUser().getUsername());

        assertNotNull(savedTrainerDto);
        assertEquals(savedTrainerDto.getUser().getFirstName(), result.getUser().getFirstName());
        assertEquals(savedTrainerDto.getSpecialization().getTrainingTypeName(), result.getSpecialization().getTrainingTypeName());
    }

    @Test
    void selectTrainer_notFound() {
        String username = "nonexistent";
        assertThrows(EntityNotFoundException.class, () -> trainerService.select(username),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void updateTrainerSuccess() {
        TrainerDto result = trainerService.create(trainerDto);

        TrainerDto updatedTrainerDto = TrainerDto.builder()
                .user(UserDto.builder()
                        .firstName("upd_FirstName")
                        .lastName("upd_LastName")
                        .username(result.getUser().getUsername())
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        TrainerDto updatedResult = trainerService.update(result.getUser().getUsername(), updatedTrainerDto);

        assertNotNull(updatedResult);
        assertEquals("upd_FirstName", updatedResult.getUser().getFirstName());
        assertEquals("Yoga", updatedResult.getSpecialization().getTrainingTypeName());
    }

    @Test
    void updateTrainer_notFound() {
        TrainerDto updatedTrainerDto = TrainerDto.builder()
                .user(UserDto.builder()
                        .firstName("upd_FirstName")
                        .lastName("upd_LastName")
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        assertThrows(EntityNotFoundException.class, () -> trainerService.update("nonexistent", updatedTrainerDto),
                "Trainer with username nonexistent wasn't found");
    }

    @Test
    void authenticateTrainerSuccess() {
        TrainerDto result = trainerService.create(trainerDto);

        String username = result.getUser().getUsername();
        String password = trainerService.forgotPassword(username);

        boolean isAuthenticated = trainerService.authenticateTrainer(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    void authenticateTrainer_notFound() {
        String username = "nonexistent";
        String password = "password";
        assertThrows(EntityNotFoundException.class, () -> trainerService.authenticateTrainer(username, password),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void changeTrainerStatusSuccess() {
        TrainerDto result = trainerService.create(trainerDto);
        String username = result.getUser().getUsername();

        trainerService.changeStatus(username, false);

        TrainerDto updatedTrainer = trainerService.select(username);
        assertFalse(updatedTrainer.getUser().getIsActive());
    }

    @Test
    void changeTrainerPasswordSuccess() {
        TrainerDto result = trainerService.create(trainerDto);

        String username = result.getUser().getUsername();
        String password = trainerService.forgotPassword(username);
        String newPassword = "newPass";

        trainerDto.getUser().setUsername(username);
        trainerDto.getUser().setPassword(password);

        trainerService.update(username, trainerDto);

        trainerService.changePassword(username, password, newPassword);

        boolean isAuthenticated = trainerService.authenticateTrainer(username, newPassword);
        assertTrue(isAuthenticated);
    }

    @Test
    void changePasswordIncorrectOldPassword() {
        TrainerDto result = trainerService.create(trainerDto);
        String username = result.getUser().getUsername();

        assertThrows(IllegalArgumentException.class, () -> trainerService.changePassword(username, "wrongPassword", "newPassword"),
                "Wrong password");
    }
}

