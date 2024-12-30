package org.example.gymcrmsystem.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
@Builder
public class TraineeDto implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    boolean isActive;
    Date dateOfBirth;
    String address;
}