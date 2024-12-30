package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.TrainingType;

import java.io.Serializable;
import java.util.Date;

@Value
@Builder
public class TrainingDto implements Serializable {
    Long id;
    Long traineeId;
    Long trainerId;
    String trainingName;
    TrainingType trainingType;
    Date date;
    Number duration;
}