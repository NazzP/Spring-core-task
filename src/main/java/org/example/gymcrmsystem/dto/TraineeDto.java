package org.example.gymcrmsystem.dto;

import lombok.*;
import org.example.gymcrmsystem.parser.Identifiable;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"firstName", "lastName", "password", "address"})
public class TraineeDto implements Serializable, Identifiable<Long> {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
    private Date dateOfBirth;
    private String address;
}
