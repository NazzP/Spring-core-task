package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.model.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeDto convertToDto(Trainee trainee);
    Trainee convertToEntity(TraineeDto traineeDto) ;
}
