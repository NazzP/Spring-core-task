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
    private Long id;
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Date date;
    private Number duration;
}
