package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;
import org.example.gymcrmsystem.model.User;

import java.io.Serializable;

@Value
@Builder
public class UserDTO implements Serializable {
    String firstName;
    String lastName;
    String username;
    String password;
    boolean isActive;

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .isActive(user.isActive())
                .build();
    }
}