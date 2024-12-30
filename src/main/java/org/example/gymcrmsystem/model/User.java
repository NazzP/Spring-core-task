package org.example.gymcrmsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
}
