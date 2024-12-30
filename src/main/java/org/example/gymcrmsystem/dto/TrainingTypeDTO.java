package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.TrainingType;

import java.io.Serializable;

@Value
@Builder
public class TrainingTypeDTO implements Serializable {
    String trainingTypeName;

    public static TrainingTypeDTO fromEntity(TrainingType trainingType) {
        return TrainingTypeDTO.builder()
                .trainingTypeName(trainingType.getTrainingTypeName())
                .build();
    }

    public TrainingType toEntity() {
        return TrainingType.builder()
                .trainingTypeName(this.trainingTypeName)
                .build();
    }
}