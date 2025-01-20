package org.example.gymcrmsystem.service;

import jakarta.transaction.Transactional;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;
import org.example.gymcrmsystem.mapper.TraineeMapper;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.entity.User;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.service.impl.TraineeServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UsernameGenerator usernameGenerator;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Mock
    private TraineeMapper traineeMapper;

    @Test
    void createTraineeSuccess() {
        TraineeDto traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        Trainee traineeToSave = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username("John.Doe")
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(usernameGenerator.generateUniqueUsername(any(UserDto.class))).thenReturn("John.Doe");
        when(passwordGenerator.generateRandomPassword()).thenReturn("encodedPassword");
        when(traineeMapper.convertToEntity(any(TraineeDto.class))).thenReturn(traineeToSave);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(traineeToSave);
        when(traineeMapper.convertToDto(any(Trainee.class))).thenReturn(traineeDto);

        TraineeDto createdTrainee = traineeService.create(traineeDto);

        assertNotNull(createdTrainee);
        assertEquals("John", createdTrainee.getUser().getFirstName());
        assertEquals("Doe", createdTrainee.getUser().getLastName());
        assertEquals("John.Doe", createdTrainee.getUser().getUsername());
        assertEquals("123 Main St", createdTrainee.getAddress());

        verify(traineeRepository, times(1)).save(any(Trainee.class));
        verify(usernameGenerator, times(1)).generateUniqueUsername(any(UserDto.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void selectTraineeSuccess() {
        String username = "FirstName.LastName";
        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        TraineeDto traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(traineeMapper.convertToDto(any(Trainee.class))).thenReturn(traineeDto);

        TraineeDto foundTrainee = traineeService.select(username);

        assertNotNull(foundTrainee);
        assertEquals("FirstName", foundTrainee.getUser().getFirstName());
        assertEquals("LastName", foundTrainee.getUser().getLastName());
        assertEquals("encodedPassword", foundTrainee.getUser().getPassword());
        assertEquals("123 Main St", foundTrainee.getAddress());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeMapper, times(1)).convertToDto(any(Trainee.class));
    }

    @Test
    void selectTraineeNotFound() {
        String username = "empty";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> traineeService.select(username),
                "Trainee with username " + username + " wasn't found");
    }

    @Test
    void updateTraineeSuccess() {
        String username = "FirstName.LastName";
        TraineeDto updatedTraineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastNameUpdated")
                        .username("FirstName.LastName")
                        .password("newPassword123")
                        .build())
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 New St")
                .build();

        Trainee existingTrainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("FirstName.LastName")
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        Trainee updatedTrainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastNameUpdated")
                        .username("FirstName.LastNameUpdated")
                        .password("encodedPassword")
                        .build())
                .address("123 New St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainee));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(passwordGenerator.generateRandomPassword()).thenReturn("encodedPassword");
        when(traineeMapper.convertToEntity(any(TraineeDto.class))).thenReturn(updatedTrainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(updatedTrainee);
        when(traineeMapper.convertToDto(any(Trainee.class))).thenReturn(updatedTraineeDto);

        TraineeDto result = traineeService.update(username, updatedTraineeDto);

        assertNotNull(result);
        assertEquals("FirstName", result.getUser().getFirstName());
        assertEquals("LastNameUpdated", result.getUser().getLastName());
        assertEquals("123 New St", result.getAddress());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void updateTraineeInvalidUsername() {
        String username = "NonExistentUsername";
        TraineeDto updatedTraineeDto = TraineeDto.builder().build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.update(username, updatedTraineeDto),
                "Trainee with username " + username + " wasn't found");
    }

    @Test
    void deleteTraineeSuccess() {
        String username = "John.Doe";
        Trainee traineeToDelete = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(traineeToDelete));

        traineeService.delete(username);

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).deleteByUsername(username);
    }


    @Test
    void deleteTraineeNotFound() {
        String username = "empty";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> traineeService.delete(username),
                "Trainee with username " + username + " wasn't found");
    }

    @Test
    void authenticateTraineeSuccess() {
        String username = "John.Doe";
        String password = "password123";
        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(passwordEncoder.matches(password, trainee.getUser().getPassword())).thenReturn(true);

        boolean isAuthenticated = traineeService.authenticateTrainee(username, password);

        assertTrue(isAuthenticated);

        verify(traineeRepository, times(1)).findByUsername(username);
    }

    @Test
    void authenticateTraineeInvalidPassword() {
        String username = "John.Doe";
        String password = "wrongPassword";

        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(passwordEncoder.matches(password, trainee.getUser().getPassword())).thenReturn(false);

        boolean isAuthenticated = traineeService.authenticateTrainee(username, password);
        assertFalse(isAuthenticated);
    }

    @Test
    void changePasswordSuccess() {
        String username = "FirstName.LastName";
        String lastPassword = "password123";
        String newPassword = "newPassword123";

        Trainee existingTrainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username(username)
                        .password("newPassword123")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainee));
        when(passwordEncoder.matches(lastPassword, existingTrainee.getUser().getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username(username)
                        .password("password123")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainee));
        when(traineeMapper.convertToEntity(any(TraineeDto.class))).thenReturn(existingTrainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        traineeService.changePassword(username, lastPassword, newPassword);
        assertEquals(newPassword, existingTrainee.getUser().getPassword());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void changePasswordWithInvalidOldPassword() {
        String username = "John.Doe";
        String oldPassword = "wrongPassword";
        String newPassword = "newPassword123";

        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(passwordEncoder.matches(oldPassword, trainee.getUser().getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> traineeService.changePassword(username, oldPassword, newPassword),
                "Wrong password");
    }

    @Test
    void forgotPasswordTraineeNotFound() {
        String username = "NonExistentUsername";

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.forgotPassword(username),
                "Trainee with username " + username + " wasn't found");
    }

    @Test
    void changeStatusSuccess() {
        String username = "John.Doe";
        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username(username)
                        .password("encodedPassword")
                        .isActive(true)
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        traineeService.changeStatus(username, false);

        assertFalse(trainee.getUser().getIsActive());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void changeStatusTraineeNotFound() {
        String username = "NonExistentUsername";

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.changeStatus(username, false),
                "Trainee with username " + username + " wasn't found");

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, never()).save(any(Trainee.class));
    }

    @Test
    void forgotPasswordSuccess() {
        String username = "John.Doe";
        String newPassword = "tempPassword";
        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username(username)
                        .password("encodedPassword")
                        .build())
                .address("123 Main St")
                .build();

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(passwordGenerator.generateRandomPassword()).thenReturn(newPassword);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedTempPassword");

        String returnedPassword = traineeService.forgotPassword(username);

        assertEquals(newPassword, returnedPassword);
        assertEquals("encodedTempPassword", trainee.getUser().getPassword());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).save(trainee);
    }
}
