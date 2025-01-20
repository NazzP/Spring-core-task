package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.entity.Training;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TraineeMapper.class, TrainerMapper.class, TrainingTypeMapper.class})
public interface TrainingMapper {
    TrainingDto convertToDto(Training training);

    Training convertToEntity(TrainingDto trainingDto);
}
