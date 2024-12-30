package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.Trainee;

import java.io.Serializable;
import java.util.Date;

@Value
@Builder
public class TraineeDTO implements Serializable {
    UserDTO userDTO;
    Date dateOfBirth;
    String address;

    public static TraineeDTO fromEntity(Trainee trainee) {
        return TraineeDTO.builder()
                .userDTO(UserDTO.builder()
                        .firstName(trainee.getFirstName())
                        .lastName(trainee.getLastName())
                        .username(trainee.getUsername())
                        .password(trainee.getPassword())
                        .isActive(trainee.isActive())
                        .build())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }

    public Trainee toEntity() {
        return Trainee.builder()
                .firstName(this.userDTO.getFirstName())
                .lastName(this.userDTO.getLastName())
                .username(this.userDTO.getUsername())
                .password(this.userDTO.getPassword())
                .isActive(this.userDTO.isActive())
                .dateOfBirth(this.dateOfBirth)
                .address(this.address)
                .build();
    }
}