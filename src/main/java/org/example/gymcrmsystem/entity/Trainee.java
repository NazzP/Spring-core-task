package org.example.gymcrmsystem.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainees")
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ToString.Exclude
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ToString.Exclude
    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Training> trainings;

    @ToString.Exclude
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "trainee_trainer", // table name
            joinColumns = @JoinColumn(name = "trainee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    )
    private List<Trainer> trainers = new ArrayList<>();
}
