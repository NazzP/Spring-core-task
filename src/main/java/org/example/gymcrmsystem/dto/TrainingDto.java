package org.example.gymcrmsystem.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDto implements Serializable {

    @NotNull(message = "trainee id is required")
    private TraineeDto trainee;

    @NotNull(message = "trainer id is required")
    private TrainerDto trainer;

    @Pattern(regexp = "^[^0-9]*$", message = "Training name name mustn't contain numbers")
    @NotNull(message = "training name is required")
    private String trainingName;

    @NotNull(message = "training type is required")
    private TrainingTypeDto trainingType;

    @NotNull(message = "date is required")
    @FutureOrPresent(message = "Training date should be in the present or future")
    private LocalDate date;

    @NotNull(message = "duration is required")
    @Positive(message = "Training duration must be positive")
    private Integer duration;
}
