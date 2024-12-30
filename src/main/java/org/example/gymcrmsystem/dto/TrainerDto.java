package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.TrainingType;

import java.io.Serializable;

@Value
@Builder
public class TrainerDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    boolean isActive;
    TrainingType specialization;
}