package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainerMapperTest {

    private TrainerMapper trainerMapper;

    @BeforeEach
    public void setUp() {
        trainerMapper = Mappers.getMapper(TrainerMapper.class);
    }

    @Test
    void convertToDto() {
        Trainer trainer = Trainer.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .username("FirstName.LastName")
                .password("password")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        TrainerDto trainerDto = trainerMapper.convertToDto(trainer);

        assertNotNull(trainerDto);
        assertEquals(trainer.getId(), trainerDto.getId());
        assertEquals(trainer.getFirstName(), trainerDto.getFirstName());
        assertEquals(trainer.getLastName(), trainerDto.getLastName());
        assertEquals(trainer.getUsername(), trainerDto.getUsername());
        assertEquals(trainer.getIsActive(), trainerDto.getIsActive());
        assertEquals(trainer.getSpecialization(), trainerDto.getSpecialization());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TrainerDto trainerDto = trainerMapper.convertToDto(null);
        assertNull(trainerDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TrainerDto trainerDto = TrainerDto.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .username("FirstName.LastName")
                .isActive(true)
                .specialization(TrainingType.builder().id(1L).trainingTypeName("Yoga").build())
                .build();

        Trainer trainer = trainerMapper.convertToEntity(trainerDto);

        assertNotNull(trainer);
        assertEquals(trainerDto.getId(), trainer.getId());
        assertEquals(trainerDto.getFirstName(), trainer.getFirstName());
        assertEquals(trainerDto.getLastName(), trainer.getLastName());
        assertEquals(trainerDto.getUsername(), trainer.getUsername());
        assertEquals(trainerDto.getIsActive(), trainer.getIsActive());
        assertEquals(trainerDto.getSpecialization(), trainer.getSpecialization());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainer trainer = trainerMapper.convertToEntity(null);
        assertNull(trainer, "Expected convertToEntity to return null when input is null");
    }
}
