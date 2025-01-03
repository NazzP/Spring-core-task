package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.model.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerDto convertToDto(Trainer trainer);
    Trainer convertToEntity(TrainerDto trainerDto) ;
}
