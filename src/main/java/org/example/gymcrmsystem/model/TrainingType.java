package org.example.gymcrmsystem.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrainingType {
    private long id;
    private String trainingTypeName;
}
