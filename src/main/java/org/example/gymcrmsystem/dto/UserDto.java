package org.example.gymcrmsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    @NotNull(message = "First name is required")
    @Pattern(regexp = "^[^0-9]*$", message = "First name mustn't contain numbers")
    @ToString.Exclude
    private String firstName;

    @NotNull(message = "Last name is required")
    @Pattern(regexp = "^[^0-9]*$", message = "Last name mustn't' contain numbers")
    @ToString.Exclude
    private String lastName;

    private String username;

    @ToString.Exclude
    private String password;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}
