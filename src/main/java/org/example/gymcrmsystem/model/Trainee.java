package org.example.gymcrmsystem.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@ToString(callSuper = true ,exclude = {"address"})
public class Trainee extends User {
    private Date dateOfBirth;
    private String address;
}
