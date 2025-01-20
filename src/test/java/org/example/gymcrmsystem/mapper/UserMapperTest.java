package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void convertToDto() {
        User user = User.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .username("FirstName.LastName")
                .password("password")
                .isActive(true)
                .build();

        UserDto userDto = userMapper.convertToDto(user);

        assertAll("userDto",
                () -> assertNotNull(userDto),
                () -> assertEquals(user.getFirstName(), userDto.getFirstName()),
                () -> assertEquals(user.getLastName(), userDto.getLastName()),
                () -> assertEquals(user.getUsername(), userDto.getUsername()),
                () -> assertEquals(user.getPassword(), userDto.getPassword()),
                () -> assertEquals(user.getIsActive(), userDto.getIsActive())
        );
    }

    @Test
    void convertToDtoWithNullTrainee() {
        UserDto userDto = userMapper.convertToDto(null);
        assertNull(userDto, "Expected convertToDto to return null when input is null");
    }

    @Test
    void convertToEntity() {
        UserDto userDto = UserDto.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .password("password")
                .username("FirstName.LastName")
                .isActive(true)
                .build();

        User user = userMapper.convertToEntity(userDto);

        assertAll("user",
                () -> assertNotNull(user),
                () -> assertEquals(userDto.getFirstName(), user.getFirstName()),
                () -> assertEquals(userDto.getLastName(), user.getLastName()),
                () -> assertEquals(userDto.getUsername(), user.getUsername()),
                () -> assertEquals(userDto.getPassword(), user.getPassword()),
                () -> assertEquals(userDto.getIsActive(), user.getIsActive())
        );
    }

    @Test
    void convertToEntityWithNullTraineeDto() {
        User user = userMapper.convertToEntity(null);
        assertNull(user, "Expected convertToEntity to return null when input is null");
    }
}
