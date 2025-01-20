package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.entity.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TraineeMapper {
    TraineeDto convertToDto(Trainee trainee);

    Trainee convertToEntity(TraineeDto traineeDto);
}
