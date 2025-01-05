package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.model.Training;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;

    @BeforeEach
    public void setUp() {
        trainingMapper = Mappers.getMapper(TrainingMapper.class);
    }

    @Test
    void convertToDto() {
        Training training = Training.builder()
                .id(1L)
                .traineeId(100L)
                .trainerId(200L)
                .trainingName("Yoga Session")
                .trainingType(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .date(new Date())
                .duration(60)
                .build();

        TrainingDto trainingDto = trainingMapper.convertToDto(training);

        assertNotNull(trainingDto);
        assertEquals(training.getId(), trainingDto.getId());
        assertEquals(training.getTraineeId(), trainingDto.getTraineeId());
        assertEquals(training.getTrainerId(), trainingDto.getTrainerId());
        assertEquals(training.getTrainingName(), trainingDto.getTrainingName());
        assertEquals(training.getTrainingType(), trainingDto.getTrainingType());
        assertEquals(training.getDate(), trainingDto.getDate());
        assertEquals(training.getDuration(), trainingDto.getDuration());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainingDto trainingDto = trainingMapper.convertToDto(null);
        assertNull(trainingDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainingDto trainingDto = TrainingDto.builder()
                .id(1L)
                .traineeId(100L)
                .trainerId(200L)
                .trainingName("Yoga Session")
                .trainingType(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .date(new Date())
                .duration(60)
                .build();

        Training training = trainingMapper.convertToEntity(trainingDto);

        assertNotNull(training);
        assertEquals(trainingDto.getId(), training.getId());
        assertEquals(trainingDto.getTraineeId(), training.getTraineeId());
        assertEquals(trainingDto.getTrainerId(), training.getTrainerId());
        assertEquals(trainingDto.getTrainingName(), training.getTrainingName());
        assertEquals(trainingDto.getTrainingType(), training.getTrainingType());
        assertEquals(trainingDto.getDate(), training.getDate());
        assertEquals(trainingDto.getDuration(), training.getDuration());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Training training = trainingMapper.convertToEntity(null);
        assertNull(training, "Expected convertToEntity to return null when input is null");
    }
}
