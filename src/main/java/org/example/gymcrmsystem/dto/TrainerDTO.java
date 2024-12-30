package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.Trainer;

import java.io.Serializable;

@Value
@Builder
public class TrainerDTO implements Serializable {
    UserDTO userDTO;
    TrainingTypeDTO specialization;

    public static TrainerDTO fromEntity(Trainer trainer) {
        return TrainerDTO.builder()
                .userDTO(UserDTO.fromEntity(trainer))
                .specialization(trainer.getSpecialization() != null
                        ? TrainingTypeDTO.fromEntity(trainer.getSpecialization())
                        : null)
                .build();
    }

    public Trainer toEntity() {
        return Trainer.builder()
                .firstName(this.userDTO.getFirstName())
                .lastName(this.userDTO.getLastName())
                .username(this.userDTO.getUsername())
                .password(this.userDTO.getPassword())
                .isActive(this.userDTO.isActive())
                .specialization(this.specialization != null
                        ? this.specialization.toEntity()
                        : null)
                .build();
    }
}