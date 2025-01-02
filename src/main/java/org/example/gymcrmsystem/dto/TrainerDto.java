package org.example.gymcrmsystem.dto;

import lombok.*;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.parser.Identifiable;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDto implements Serializable, Identifiable<Long> {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    Boolean isActive;
    TrainingType specialization;
}