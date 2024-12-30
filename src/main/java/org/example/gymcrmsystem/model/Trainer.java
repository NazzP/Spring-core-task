package org.example.gymcrmsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Trainer extends User {
    private TrainingType specialization;
}
