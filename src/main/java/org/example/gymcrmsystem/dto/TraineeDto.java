package org.example.gymcrmsystem.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    Boolean isActive;
    Date dateOfBirth;
    String address;
}