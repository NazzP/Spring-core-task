package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.model.Trainee;
import org.springframework.stereotype.Component;

@Component
public class TraineeMapper {

    public TraineeDto convertToDto(Trainee trainee) {
        return TraineeDto.builder()
                .id(trainee.getId())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .username(trainee.getUsername())
                .password(trainee.getPassword())
                .isActive(trainee.isActive())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }

    public Trainee convertToEntity(TraineeDto traineeDto) {
        return Trainee.builder()
                .id(traineeDto.getId())
                .firstName(traineeDto.getFirstName())
                .lastName(traineeDto.getLastName())
                .username(traineeDto.getUsername())
                .password(traineeDto.getPassword())
                .isActive(traineeDto.isActive())
                .dateOfBirth(traineeDto.getDateOfBirth())
                .address(traineeDto.getAddress())
                .build();
    }
}
