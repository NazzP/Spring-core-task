package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.dto.*;
import org.example.gymcrmsystem.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TrainingMapperTest {

    @Autowired
    private TrainingMapper trainingMapper;

    @Test
    void convertToDto() {

        Training training = Training.builder()
                .trainee(Trainee.builder()
                        .user(User.builder()
                                .firstName("FirstName")
                                .lastName("LastName")
                                .username("FirstName.LastName")
                                .password("password")
                                .isActive(true).build())
                        .dateOfBirth(LocalDate.of(2020, 1, 1))
                        .address("123 Main St")
                        .build())
                .trainer(Trainer.builder()
                        .user(User.builder()
                                .firstName("FirstName")
                                .lastName("LastName")
                                .username("FirstName.LastName")
                                .password("password")
                                .isActive(true)
                                .build())
                        .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                        .build())
                .trainingType(TrainingType.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .trainingName("TrainingName")
                .date(LocalDate.of(2020, 1, 1))
                .duration(60)
                .build();

        TrainingDto trainingDto = trainingMapper.convertToDto(training);

        assertAll("trainingDto",
                () -> assertNotNull(trainingDto),
                () -> assertEquals(training.getTrainee().getUser().getFirstName(), trainingDto.getTrainee().getUser().getFirstName()),
                () -> assertEquals(training.getTrainee().getAddress(), trainingDto.getTrainee().getAddress()),
                () -> assertEquals(training.getTrainer().getUser().getFirstName(), trainingDto.getTrainer().getUser().getFirstName()),
                () -> assertEquals(training.getTrainer().getSpecialization().getTrainingTypeName(), trainingDto.getTrainer().getSpecialization().getTrainingTypeName()),
                () -> assertEquals(training.getTrainingType().getTrainingTypeName(), trainingDto.getTrainingType().getTrainingTypeName()),
                () -> assertEquals(training.getTrainingName(), trainingDto.getTrainingName()),
                () -> assertEquals(training.getDate(), trainingDto.getDate()),
                () -> assertEquals(training.getDuration(), trainingDto.getDuration())
        );
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainingDto trainingDto = trainingMapper.convertToDto(null);
        assertNull(trainingDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainingDto trainingDto = TrainingDto.builder()
                .trainee(TraineeDto.builder()
                        .user(UserDto.builder()
                                .firstName("FirstName")
                                .lastName("LastName")
                                .username("FirstName.LastName")
                                .password("password")
                                .isActive(true).build())
                        .dateOfBirth(LocalDate.of(2020, 1, 1))
                        .address("123 Main St")
                        .build())
                .trainer(TrainerDto.builder()
                        .user(UserDto.builder()
                                .firstName("FirstName")
                                .lastName("LastName")
                                .username("FirstName.LastName")
                                .password("password")
                                .isActive(true)
                                .build())
                        .specialization(TrainingTypeDto.builder().trainingTypeName("Yoga").build())
                        .build())
                .trainingType(TrainingTypeDto.builder()
                        .trainingTypeName("Yoga")
                        .build())
                .trainingName("TrainingName")
                .date(LocalDate.of(2020, 1, 1))
                .duration(60)
                .build();

        Training training = trainingMapper.convertToEntity(trainingDto);

        assertAll("training",
                () -> assertNotNull(training),
                () -> assertEquals(training.getTrainee().getUser().getFirstName(), trainingDto.getTrainee().getUser().getFirstName()),
                () -> assertEquals(training.getTrainee().getAddress(), trainingDto.getTrainee().getAddress()),
                () -> assertEquals(training.getTrainer().getUser().getFirstName(), trainingDto.getTrainer().getUser().getFirstName()),
                () -> assertEquals(training.getTrainer().getSpecialization().getTrainingTypeName(), trainingDto.getTrainer().getSpecialization().getTrainingTypeName()),
                () -> assertEquals(training.getTrainingType().getTrainingTypeName(), trainingDto.getTrainingType().getTrainingTypeName()),
                () -> assertEquals(training.getTrainingName(), trainingDto.getTrainingName()),
                () -> assertEquals(training.getDate(), trainingDto.getDate()),
                () -> assertEquals(training.getDuration(), trainingDto.getDuration())
        );

    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Training training = trainingMapper.convertToEntity(null);
        assertNull(training, "Expected convertToEntity to return null when input is null");
    }
}
