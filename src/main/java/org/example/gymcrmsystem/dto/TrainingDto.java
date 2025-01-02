package org.example.gymcrmsystem.dto;

import lombok.*;
import org.example.gymcrmsystem.model.TrainingType;
import org.example.gymcrmsystem.parser.Identifiable;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDto implements Serializable, Identifiable<Long> {
    Long id;
    Long traineeId;
    Long trainerId;
    String trainingName;
    TrainingType trainingType;
    Date date;
    Number duration;
}