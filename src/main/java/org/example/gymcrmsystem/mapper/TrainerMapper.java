package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.entity.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TrainingTypeMapper.class})
public interface TrainerMapper {
    TrainerDto convertToDto(Trainer trainer);

    Trainer convertToEntity(TrainerDto trainerDto);
}
