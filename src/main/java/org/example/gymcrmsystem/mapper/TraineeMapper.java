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

    public Trainee convertToEntity(TraineeDto traineeDTO) {
        return Trainee.builder()
                .id(traineeDTO.getId())
                .firstName(traineeDTO.getFirstName())
                .lastName(traineeDTO.getLastName())
                .username(traineeDTO.getUsername())
                .password(traineeDTO.getPassword())
                .isActive(traineeDTO.isActive())
                .dateOfBirth(traineeDTO.getDateOfBirth())
                .address(traineeDTO.getAddress())
                .build();
    }
}
