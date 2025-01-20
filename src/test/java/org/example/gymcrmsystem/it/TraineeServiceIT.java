package org.example.gymcrmsystem.it;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.service.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("prod")
class TraineeServiceIT {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private TraineeDto traineeDto;

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("datasource.url", postgres::getJdbcUrl);
        registry.add("datasource.username", postgres::getUsername);
        registry.add("datasource.password", postgres::getPassword);
    }

    @Test
    void isPostgresRunningTest() {
        Assertions.assertTrue(postgres.isRunning());
    }

    @BeforeEach
    void setup() {
        traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("123 Main str")
                .build();
    }

    @Test
    void createTraineeSuccess() {
        TraineeDto result = traineeService.create(traineeDto);

        assertNotNull(result);
        assertEquals("FirstName", result.getUser().getFirstName());
        assertEquals("LastName", result.getUser().getLastName());
        assertEquals("FirstName.LastName", result.getUser().getUsername());

        Trainee savedTrainee = traineeRepository.findByUsername(result.getUser().getUsername()).orElse(null);
        assertNotNull(savedTrainee);
        assertEquals(savedTrainee.getUser().getFirstName(), result.getUser().getFirstName());
        assertEquals(savedTrainee.getAddress(), result.getAddress());
    }

    @Test
    void selectTraineeSuccess() {
        TraineeDto result = traineeService.create(traineeDto);
        TraineeDto savedTraineeDro = traineeService.select(traineeDto.getUser().getUsername());

        assertNotNull(savedTraineeDro);
        assertEquals(savedTraineeDro.getUser().getFirstName(), result.getUser().getFirstName());
        assertEquals(savedTraineeDro.getAddress(), result.getAddress());
    }

    @Test
    void selectTrainee_notFound() {
        String selectedUsername = "Non exising username";
        assertThrows(EntityNotFoundException.class, () -> traineeService.select(selectedUsername),
                "Trainee with username Non exising username wasn't found");
    }

    @Test
    void updateTraineeSuccess() {
        String updatedFirstName = "upd_FirstName";
        String updatedAddress = "upd_address";
        TraineeDto result = traineeService.create(traineeDto);

        TraineeDto updatedTraineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName(updatedFirstName)
                        .lastName("upd_LastName")
                        .username(result.getUser().getUsername())
                        .password(result.getUser().getPassword())
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address(updatedAddress)
                .build();

        TraineeDto updatedTraineeDro = traineeService.update(traineeDto.getUser().getUsername(), updatedTraineeDto);

        assertNotNull(updatedTraineeDro);
        assertEquals(updatedTraineeDro.getUser().getFirstName(), updatedFirstName);
        assertEquals(updatedTraineeDro.getAddress(), updatedAddress);
    }

    @Test
    void updateTrainee_notFound() {
        String updatedFirstName = "upd_FirstName";
        String updatedAddress = "upd_address";

        TraineeDto updatedTraineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName(updatedFirstName)
                        .lastName("upd_LastName")
                        .password("any pass")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address(updatedAddress)
                .build();

        assertThrows(EntityNotFoundException.class, () -> traineeService.update("Non exising username", updatedTraineeDto),
                "Trainee with username Non exising username wasn't found");
    }

    @Test
    void deleteTraineeSuccess() {
        TraineeDto result = traineeService.create(traineeDto);

        assertNotNull(result);

        traineeService.delete(result.getUser().getUsername());
        Trainee deletedTrainee = traineeRepository.findByUsername(result.getUser().getUsername()).orElse(null);
        assertNull(deletedTrainee);
    }

    @Test
    void deleteTrainee_notFound() {
        assertThrows(EntityNotFoundException.class, () -> traineeService.delete("Non exising username"),
                "Trainee with username Non exising username wasn't found");
    }

    @Test
    void authenticateTraineeSuccess() {
        TraineeDto result = traineeService.create(traineeDto);

        String username = result.getUser().getUsername();
        String password = traineeService.forgotPassword(username);

        boolean isAuthenticated = traineeService.authenticateTrainee(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    void authenticateTraineeNotFound() {
        String nonExistingUsername = "nonexistent";
        String password = "password";
        Exception exception = assertThrows(EntityNotFoundException.class, () -> traineeService.authenticateTrainee(nonExistingUsername, password));
        assertEquals("Trainee with username " + nonExistingUsername + " wasn't found", exception.getMessage());
    }

    @Test
    void authenticateTraineeUsernameNotFound() {
        TraineeDto result = traineeService.create(traineeDto);
        String username = "random";
        String password = result.getUser().getPassword();
        assertThrows(EntityNotFoundException.class, () -> traineeService.authenticateTrainee(username, password),
                "Trainee with username random wasn't found");
    }

    @Test
    void authenticateTraineePasswordDoesntMatch() {
        TraineeDto result = traineeService.create(traineeDto);
        String username = result.getUser().getUsername();
        String password = "random";

        boolean isAuthenticated = traineeService.authenticateTrainee(username, password);
        assertFalse(isAuthenticated);
    }

    @Test
    void changeTraineePasswordSuccess() {
        TraineeDto result = traineeService.create(traineeDto);

        String username = result.getUser().getUsername();
        String password = traineeService.forgotPassword(username);

        String newPassword = "newPass";

        traineeDto.getUser().setUsername(username);
        traineeDto.getUser().setPassword(passwordEncoder.encode(newPassword));

        traineeService.changePassword(username, password, newPassword);

        boolean isAuthenticated = traineeService.authenticateTrainee(username, newPassword);
        assertTrue(isAuthenticated);
    }

    @Test
    void changeTraineePasswordTraineeNotFound() {
        String nonExistingUsername = "nonexistent";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        Exception exception = assertThrows(EntityNotFoundException.class, () -> traineeService.changePassword(nonExistingUsername, oldPassword, newPassword));
        assertEquals("Trainee with username " + nonExistingUsername + " wasn't found", exception.getMessage());
    }

    @Test
    void changePasswordIncorrectOldPassword() {
        TraineeDto dto = traineeService.create(traineeDto);
        String username = dto.getUser().getUsername();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> traineeService.changePassword(username, "wrongPassword", "newPassword"));
        assertEquals("Wrong password", exception.getMessage());
    }

    @Test
    void changeTraineeStatusSuccess() {
        TraineeDto result = traineeService.create(traineeDto);
        String username = result.getUser().getUsername();

        traineeService.changeStatus(username, false);

        TraineeDto updatedTrainee = traineeService.select(username);
        assertFalse(updatedTrainee.getUser().getIsActive());
    }
}
