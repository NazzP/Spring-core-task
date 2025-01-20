package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TraineeMapperTest {

    @Autowired
    private TraineeMapper traineeMapper;

    @Test
    void convertToDto() {
        Trainee trainee = Trainee.builder()
                .user(User.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("FirstName.LastName")
                        .password("password")
                        .isActive(true).build())
                .dateOfBirth(LocalDate.of(2020, 1, 1))
                .address("123 Main St")
                .build();

        TraineeDto traineeDto = traineeMapper.convertToDto(trainee);

        assertAll("traineeDto",
                () -> assertNotNull(traineeDto),
                () -> assertEquals(trainee.getUser().getFirstName(), traineeDto.getUser().getFirstName(), "First name should match"),
                () -> assertEquals(trainee.getUser().getLastName(), traineeDto.getUser().getLastName(), "Last name should match"),
                () -> assertEquals(trainee.getUser().getUsername(), traineeDto.getUser().getUsername(), "Username should match"),
                () -> assertEquals(trainee.getUser().getIsActive(), traineeDto.getUser().getIsActive(), "Active status should match"),
                () -> assertEquals(trainee.getDateOfBirth(), traineeDto.getDateOfBirth(), "Date of birth should match"),
                () -> assertEquals(trainee.getAddress(), traineeDto.getAddress(), "Address should match")
        );
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TraineeDto traineeDto = traineeMapper.convertToDto(null);
        assertNull(traineeDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TraineeDto traineeDto = TraineeDto.builder()
                .user(UserDto.builder()
                        .firstName("FirstName")
                        .lastName("LastName")
                        .username("FirstName.LastName")
                        .isActive(true)
                        .build())
                .dateOfBirth(LocalDate.of(2020, 1, 1))
                .address("123 Main St")
                .build();

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);

        assertAll("trainee",
                () -> assertNotNull(trainee),
                () -> assertEquals(traineeDto.getUser().getFirstName(), trainee.getUser().getFirstName()),
                () -> assertEquals(traineeDto.getUser().getLastName(), trainee.getUser().getLastName()),
                () -> assertEquals(traineeDto.getUser().getUsername(), trainee.getUser().getUsername()),
                () -> assertEquals(traineeDto.getUser().getIsActive(), trainee.getUser().getIsActive()),
                () -> assertEquals(traineeDto.getDateOfBirth(), trainee.getDateOfBirth()),
                () -> assertEquals(traineeDto.getAddress(), trainee.getAddress())
        );
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainee trainee = traineeMapper.convertToEntity(null);
        assertNull(trainee, "Expected convertToEntity to return null when input is null");
    }
}