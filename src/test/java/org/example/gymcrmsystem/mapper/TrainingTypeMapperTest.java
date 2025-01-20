package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.dto.TrainingTypeDto;
import org.example.gymcrmsystem.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TrainingTypeMapperTest {

    @Autowired
    private TrainingTypeMapper trainingTypeMapper;

    @Test
    void convertToDto() {

        TrainingType trainingType = TrainingType.builder()
                .trainingTypeName("Yoga")
                .build();

        TrainingTypeDto trainingTypeDto = trainingTypeMapper.convertToDto(trainingType);

        assertAll("trainingTypeDto",
                () -> assertNotNull(trainingTypeDto),
                () -> assertEquals(trainingType.getTrainingTypeName(), trainingTypeDto.getTrainingTypeName())
        );
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainingTypeDto trainingTypeDto = trainingTypeMapper.convertToDto(null);
        assertNull(trainingTypeDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainingTypeDto trainingTypeDto = TrainingTypeDto.builder()
                .trainingTypeName("Yoga")
                .build();

        TrainingType trainingType = trainingTypeMapper.convertToEntity(trainingTypeDto);

        assertAll("trainingType",
                () -> assertNotNull(trainingType),
                () -> assertEquals(trainingTypeDto.getTrainingTypeName(), trainingType.getTrainingTypeName())
        );

    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        TrainingType trainingType = trainingTypeMapper.convertToEntity(null);
        assertNull(trainingType, "Expected convertToEntity to return null when input is null");
    }
}
