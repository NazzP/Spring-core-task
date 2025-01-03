package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.model.Training;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingDto convertToDto(Training training);
    Training convertToEntity(TrainingDto trainingDto);
}
