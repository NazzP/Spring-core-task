package org.example.gymcrmsystem.dto;

import lombok.*;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.parser.Identifiable;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"firstName", "lastName", "password"})
public class TrainerDto implements Serializable, Identifiable<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
    private TrainingType specialization;
}
