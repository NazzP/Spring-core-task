package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.Training;

import java.io.Serializable;
import java.util.Date;

@Value
@Builder
public class TrainingDTO implements Serializable {
    Long traineeId;
    Long trainerId;
    String trainingName;
    TrainingTypeDTO trainingTypeDTO;
    Date date;
    Number duration;

    public static TrainingDTO fromEntity(Training training) {
        return TrainingDTO.builder()
                .traineeId(training.getTraineeId())
                .trainerId(training.getTrainerId())
                .trainingName(training.getTrainingName())
                .trainingTypeDTO(TrainingTypeDTO.fromEntity(training.getTrainingType()))
                .date(training.getDate())
                .duration(training.getDuration())
                .build();
    }

    public Training toEntity() {
        return Training.builder()
                .traineeId(this.traineeId)
                .trainerId(this.trainerId)
                .trainingName(this.trainingName)
                .trainingType(this.trainingTypeDTO != null ? this.trainingTypeDTO.toEntity() : null)
                .date(this.date)
                .duration(this.duration)
                .build();
    }
}