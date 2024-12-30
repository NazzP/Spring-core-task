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

    public Training convertToEntity(TrainingDto trainingDTO) {
        return Training.builder()
                .id(trainingDTO.getId())
                .traineeId(trainingDTO.getTraineeId())
                .trainerId(trainingDTO.getTrainerId())
                .trainingName(trainingDTO.getTrainingName())
                .trainingType(trainingDTO.getTrainingType())
                .date(trainingDTO.getDate())
                .duration(trainingDTO.getDuration())
                .build();
    }
}