package org.example.gymcrmsystem.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDto implements Serializable {

    @NotNull(message = "User cannot be null")
    private UserDto user;

    @NotNull(message = "specialization is required")
    private TrainingTypeDto specialization;
}
