package org.example.gymcrmsystem.service;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.dto.TrainingTypeDto;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TrainerMapper;
import org.example.gymcrmsystem.mapper.TrainingTypeMapper;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.entity.User;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.example.gymcrmsystem.service.impl.TrainerServiceImpl;
import org.example.gymcrmsystem.utils.PasswordGenerator;
import org.example.gymcrmsystem.utils.UsernameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void createTrainerSuccess() {
        TrainerDto trainerDto = TrainerDto.builder()
                .user(UserDto.builder().firstName("FirstName").lastName("LastName").build())
                .specialization(new TrainingTypeDto("Yoga"))
                .build();

        Trainer trainerToSave = Trainer.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("FirstName.LastName")
                        .password("encodedPassword")
                        .build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        TrainingType trainingType = TrainingType.builder()
                .trainingTypeName("Yoga")
                .build();

        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.of(trainingType));
        when(usernameGenerator.generateUniqueUsername(any())).thenReturn("FirstName.LastName");
        when(passwordGenerator.generateRandomPassword()).thenReturn("randomPassword");
        when(trainerMapper.convertToEntity(any())).thenReturn(trainerToSave);
        when(trainerRepository.save(any())).thenReturn(trainerToSave);
        when(trainerMapper.convertToDto(any())).thenReturn(trainerDto);

        TrainerDto createdTrainer = trainerService.create(trainerDto);

        assertNotNull(createdTrainer);
        assertEquals("FirstName", createdTrainer.getUser().getFirstName());
        assertEquals("LastName", createdTrainer.getUser().getLastName());
        assertEquals("FirstName.LastName", createdTrainer.getUser().getUsername());
        assertEquals("Yoga", createdTrainer.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void createTrainerTrainingTypeNotFound() {
        TrainerDto trainerDto = TrainerDto.builder()
                .user(UserDto.builder().firstName("FirstName").lastName("LastName").build())
                .specialization(new TrainingTypeDto("NonExistentType"))
                .build();

        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.create(trainerDto),
                "TrainingType with name NonExistentType wasn't found");
    }

    @Test
    void selectTrainerSuccess() {
        String username = "john.doe";
        Trainer trainer = Trainer.builder()
                .user(User.builder().firstName("John").lastName("Doe").username(username).password("encodedPassword").build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        TrainerDto trainerDto = TrainerDto.builder()
                .user(UserDto.builder().firstName("John").lastName("Doe").username(username).password("encodedPassword").build())
                .specialization(new TrainingTypeDto("Yoga"))
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(trainerMapper.convertToDto(any())).thenReturn(trainerDto);
        TrainerDto foundTrainer = trainerService.select(username);

        assertNotNull(foundTrainer);
        assertEquals("John", foundTrainer.getUser().getFirstName());
        assertEquals("Doe", foundTrainer.getUser().getLastName());
        assertEquals("Yoga", foundTrainer.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).findByUsername(username);
    }

    @Test
    void selectTrainerNotFound() {
        String username = "nonexistent";
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.select(username),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void updateTrainerSuccess() {
        String username = "john.doe";
        TrainerDto updatedTrainerDto = TrainerDto.builder()
                .user(UserDto.builder().firstName("John").lastName("DoeUpdated").username("john.doe").password("newPassword123").build())
                .specialization(new TrainingTypeDto("Cardio"))
                .build();

        Trainer existingTrainer = Trainer.builder()
                .user(User.builder().firstName("John").lastName("Doe").username("john.doe").password("encodedPassword").build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        Trainer updatedTrainer = Trainer.builder()
                .user(User.builder().firstName("John").lastName("DoeUpdated").username("john.doe").password("encodedPassword").build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Cardio")
                        .build())
                .build();

        TrainingType updatedTrainingType = TrainingType.builder()
                .trainingTypeName("Cardio")
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainer));
        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.of(updatedTrainingType));
        when(trainerMapper.convertToEntity(any())).thenReturn(updatedTrainer);
        when(trainingTypeMapper.convertToEntity(any())).thenReturn(updatedTrainingType);
        when(trainerRepository.save(any())).thenReturn(updatedTrainer);
        when(trainerMapper.convertToDto(any())).thenReturn(updatedTrainerDto);

        TrainerDto result = trainerService.update(username, updatedTrainerDto);

        assertNotNull(result);
        assertEquals("John", result.getUser().getFirstName());
        assertEquals("DoeUpdated", result.getUser().getLastName());
        assertEquals("Cardio", result.getSpecialization().getTrainingTypeName());

        verify(trainerRepository, times(1)).findByUsername(username);
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void updateTrainerTrainingTypeNotFound() {
        String username = "john.doe";
        TrainerDto updatedTrainerDto = TrainerDto.builder()
                .user(UserDto.builder().firstName("John").lastName("DoeUpdated").build())
                .specialization(new TrainingTypeDto("NonExistentType"))
                .build();

        Trainer existingTrainer = Trainer.builder()
                .user(User.builder().username("john.doe").build())
                .specialization(TrainingType.builder().trainingTypeName("Yoga").build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainer));
        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.update(username, updatedTrainerDto),
                "TrainingType with name NonExistentType wasn't found");
    }

    @Test
    void updateTrainerNotFound() {
        String username = "nonexistent";
        TrainerDto trainerDto = TrainerDto.builder().build();
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.update(username, trainerDto),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void authenticateTrainerSuccess() {
        String username = "john.doe";
        String password = "password123";
        Trainer trainer = Trainer.builder()
                .user(User.builder().username(username).password("encodedPassword").build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordEncoder.matches(password, trainer.getUser().getPassword())).thenReturn(true);

        boolean isAuthenticated = trainerService.authenticateTrainer(username, password);

        assertTrue(isAuthenticated);

        verify(trainerRepository, times(1)).findByUsername(username);
    }

    @Test
    void authenticateTrainerFailure() {
        String username = "john.doe";
        String password = "wrongPassword";
        Trainer trainer = Trainer.builder()
                .user(User.builder().username(username).password("encodedPassword").build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordEncoder.matches(password, trainer.getUser().getPassword())).thenReturn(false);

        boolean isAuthenticated = trainerService.authenticateTrainer(username, password);

        assertFalse(isAuthenticated);
    }

    @Test
    void authenticateTrainerNotFound() {
        String username = "nonexistent";
        String pass = "nonexistent";

        assertThrows(EntityNotFoundException.class, () -> trainerService.authenticateTrainer(username, pass),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void changeStatusSuccess() {
        String username = "john.doe";
        Trainer trainer = Trainer.builder()
                .user(User.builder().username(username).isActive(true).build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));

        trainerService.changeStatus(username, false);

        assertFalse(trainer.getUser().getIsActive());

        verify(trainerRepository, times(1)).findByUsername(username);
    }

    @Test
    void changeStatusTrainerNotFound() {
        String username = "nonexistent";
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.changeStatus(username, true),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void changePasswordSuccess() {
        String username = "FirstName.LastName";
        String lastPassword = "password123";
        String newPassword = "newPassword123";

        Trainee trainer = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username(username)
                        .password("password123")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordEncoder.matches(lastPassword, trainer.getUser().getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("newPassword123");

        Trainer existingTrainer = Trainer.builder()
                .user(User.builder().username(username).password("password123").build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainer));
        when(trainerMapper.convertToEntity(any(TrainerDto.class))).thenReturn(existingTrainer);
        when(trainerRepository.save(any(Trainer.class))).thenReturn(existingTrainer);

        trainerService.changePassword(username, lastPassword, newPassword);
        assertEquals(newPassword, existingTrainer.getUser().getPassword());

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void changePasswordWrongOldPassword() {
        String username = "john.doe";
        String lastPassword = "wrongPassword";
        String newPassword = "newPassword123";
        Trainer trainer = Trainer.builder()
                .user(User.builder().username(username).password("encodedPassword").build())
                .build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordEncoder.matches(lastPassword, trainer.getUser().getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> trainerService.changePassword(username, lastPassword, newPassword),
                "Wrong password");
    }

    @Test
    void changePasswordTrainingTypeNotFound() {
        String username = "john.doe";
        String lastPassword = "password123";
        String newPassword = "newPassword123";

        when(trainingTypeRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.changePassword(username, lastPassword, newPassword),
                "TrainingType with name NonExistentType wasn't found");
    }

    @Test
    void getUnassignedTrainersList() {
        String traineeUsername = "trainee1";

        Trainee trainee1 = Trainee.builder()
                .user(User.builder()
                        .username("trainee1")
                        .firstName("FirstName")
                        .lastName("LastName")
                        .password("trainerPassword123")
                        .build())
                .address("123 Main St")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        Trainer trainer1 = Trainer.builder()
                .trainees(List.of(trainee1))
                .user(User.builder()
                        .firstName("Trainer1")
                        .lastName("Last1")
                        .username("trainer1")
                        .password("trainerPassword123")
                        .build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        Trainer trainer2 = Trainer.builder()
                .trainees(List.of())
                .user(User.builder()
                        .firstName("Trainer2")
                        .lastName("Last2")
                        .username("trainer2")
                        .password("trainerPassword123")
                        .build())
                .specialization(TrainingType.builder()
                        .trainingTypeName("Pilates")
                        .build())
                .build();

        TrainerDto trainerDto1 = TrainerDto.builder()
                .user(UserDto.builder()
                        .username("trainer1")
                        .firstName("Trainer1")
                        .lastName("Last1")
                        .password("trainerPassword123")
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .build();

        TrainerDto trainerDto2 = TrainerDto.builder()
                .user(UserDto.builder()
                        .username("trainer2")
                        .firstName("Trainer2")
                        .lastName("Last2")
                        .password("trainerPassword123")
                        .build())
                .specialization(TrainingTypeDto.builder()
                        .trainingTypeName("Pilates")
                        .build())
                .build();

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee1));
        when(trainerMapper.convertToDto(trainer1)).thenReturn(trainerDto1);
        when(trainerMapper.convertToDto(trainer2)).thenReturn(trainerDto2);
        when(trainerRepository.findAll()).thenReturn(List.of(trainer1, trainer2));

        List<TrainerDto> unassignedTrainers = trainerService.getUnassignedTrainersList(traineeUsername);

        assertNotNull(unassignedTrainers);
        assertEquals(1, unassignedTrainers.size());

        assertEquals("trainer2", unassignedTrainers.get(0).getUser().getUsername());

        verify(traineeRepository, times(1)).findByUsername(traineeUsername);
        verify(trainerRepository, times(1)).findAll();
    }


    @Test
    void getUnassignedTrainersListNotFound() {
        String traineeUsername = "nonExist";

        when(trainingTypeRepository.findByName(traineeUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.getUnassignedTrainersList(traineeUsername),
                "Trainee with username nonExist wasn't found");
    }


    @Test
    void updateTrainersListSuccess() {
        String traineeUsername = "traineeUsername";
        List<String> trainersUsernames = List.of("trainer1", "trainer2");

        Trainee trainee = Trainee.builder()
                .user(User.builder().username(traineeUsername).build())
                .build();
        Trainer trainer1 = Trainer.builder().user(User.builder().username("trainer1").build()).build();
        Trainer trainer2 = Trainer.builder().user(User.builder().username("trainer2").build()).build();

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername("trainer1")).thenReturn(Optional.of(trainer1));
        when(trainerRepository.findByUsername("trainer2")).thenReturn(Optional.of(trainer2));
        when(trainerMapper.convertToDto(any())).thenReturn(new TrainerDto());

        List<TrainerDto> updatedTrainers = trainerService.updateTrainersList(traineeUsername, trainersUsernames);

        assertNotNull(updatedTrainers);
        assertEquals(2, updatedTrainers.size());

        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void updateTrainersListTraineeNotFound() {
        String traineeUsername = "nonexistentTrainee";
        List<String> trainersUsernames = List.of("trainer1", "trainer2");

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.updateTrainersList(traineeUsername, trainersUsernames),
                "Trainee with username " + traineeUsername + " wasn't found");
    }

    @Test
    void forgotPasswordSuccess() {
        String username = "trainer1";
        String tempPassword = "temp123";
        Trainer trainer = Trainer.builder().user(User.builder().username(username).build()).build();

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordGenerator.generateRandomPassword()).thenReturn(tempPassword);
        when(passwordEncoder.encode(tempPassword)).thenReturn("encodedTempPassword");

        String result = trainerService.forgotPassword(username);

        assertEquals(tempPassword, result);
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void forgotPasswordTrainerNotFound() {
        String username = "nonexistentTrainer";

        when(trainerRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.forgotPassword(username),
                "Trainer with username " + username + " wasn't found");
    }

    @Test
    void updateTrainersListTrainerNotFound() {
        String traineeUsername = "trainee1";
        List<String> trainersUsernames = List.of("trainer1", "nonexistentTrainer");

        Trainee trainee = Trainee.builder().user(User.builder().username(traineeUsername).build()).build();
        Trainer trainer1 = Trainer.builder().user(User.builder().username("trainer1").build()).build();

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername("trainer1")).thenReturn(Optional.of(trainer1));
        when(trainerRepository.findByUsername("nonexistentTrainer")).thenReturn(Optional.empty());

        List<TrainerDto> updatedTrainers = trainerService.updateTrainersList(traineeUsername, trainersUsernames);

        assertEquals(1, updatedTrainers.size());
    }
}
