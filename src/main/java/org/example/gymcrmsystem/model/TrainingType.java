package org.example.gymcrmsystem.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrainingType implements Serializable {
    private long id;
    private String trainingTypeName;
}
