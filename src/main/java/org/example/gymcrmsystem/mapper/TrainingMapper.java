package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.model.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public TrainingDto convertToDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .traineeId(training.getTraineeId())
                .trainerId(training.getTrainerId())
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .date(training.getDate())
                .duration(training.getDuration())
                .build();
    }

    public Training convertToEntity(TrainingDto trainingDto) {
        return Training.builder()
                .id(trainingDto.getId())
                .traineeId(trainingDto.getTraineeId())
                .trainerId(trainingDto.getTrainerId())
                .trainingName(trainingDto.getTrainingName())
                .trainingType(trainingDto.getTrainingType())
                .date(trainingDto.getDate())
                .duration(trainingDto.getDuration())
                .build();
    }
}