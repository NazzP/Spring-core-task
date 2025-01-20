package org.example.gymcrmsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.parser.Identifiable;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDto implements Serializable, Identifiable<Long> {
    private Long id;
    @ToString.Exclude
    private String firstName;
    @ToString.Exclude
    private String lastName;
    private String username;
    @ToString.Exclude
    private String password;
    private Boolean isActive;
    private TrainingType specialization;
}
