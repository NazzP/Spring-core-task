package org.example.gymcrmsystem.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;

class TraineeMapperTest {

    private TraineeMapper traineeMapper;

    @BeforeEach
    public void setUp() {
        traineeMapper = Mappers.getMapper(TraineeMapper.class);
    }

    @Test
    void convertToDto() {
        Trainee trainee = Trainee.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .username("FirstName.LastName")
                .password("password")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();

        TraineeDto traineeDto = traineeMapper.convertToDto(trainee);

        assertNotNull(traineeDto);
        assertEquals(trainee.getId(), traineeDto.getId());
        assertEquals(trainee.getFirstName(), traineeDto.getFirstName());
        assertEquals(trainee.getLastName(), traineeDto.getLastName());
        assertEquals(trainee.getUsername(), traineeDto.getUsername());
        assertEquals(trainee.getIsActive(), traineeDto.getIsActive());
        assertEquals(trainee.getDateOfBirth(), traineeDto.getDateOfBirth());
        assertEquals(trainee.getAddress(), traineeDto.getAddress());
    }

    @Test
    void convertToDtoWithNullTrainee() {
        TraineeDto traineeDto = traineeMapper.convertToDto(null);
        assertNull(traineeDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        TraineeDto traineeDto = TraineeDto.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .username("FirstName.LastName")
                .isActive(true)
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();

        Trainee trainee = traineeMapper.convertToEntity(traineeDto);

        assertNotNull(trainee);
        assertEquals(traineeDto.getId(), trainee.getId());
        assertEquals(traineeDto.getFirstName(), trainee.getFirstName());
        assertEquals(traineeDto.getLastName(), trainee.getLastName());
        assertEquals(traineeDto.getUsername(), trainee.getUsername());
        assertEquals(traineeDto.getIsActive(), trainee.getIsActive());
        assertEquals(traineeDto.getDateOfBirth(), trainee.getDateOfBirth());
        assertEquals(traineeDto.getAddress(), trainee.getAddress());
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        Trainee trainee = traineeMapper.convertToEntity(null);
        assertNull(trainee, "Expected convertToEntity to return null when input is null");
    }
}

