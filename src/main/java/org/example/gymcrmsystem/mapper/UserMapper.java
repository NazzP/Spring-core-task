package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto);
}
