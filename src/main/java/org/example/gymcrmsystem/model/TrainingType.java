package org.example.gymcrmsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrainingType implements Serializable {
    private long id;
    private String trainingTypeName;
}
