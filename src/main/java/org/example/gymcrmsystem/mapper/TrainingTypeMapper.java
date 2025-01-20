package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainingTypeDto;
import org.example.gymcrmsystem.entity.TrainingType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {
    TrainingTypeDto convertToDto(TrainingType trainingType);

    TrainingType convertToEntity(TrainingTypeDto trainingTypeDto);
}
