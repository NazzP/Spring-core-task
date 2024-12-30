package org.example.gymcrmsystem.mapper;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.model.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper {

    public TrainerDto convertToDto(Trainer trainer) {
        return TrainerDto.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .username(trainer.getUsername())
                .password(trainer.getPassword())
                .isActive(trainer.isActive())
                .specialization(trainer.getSpecialization())
                .build();
    }

    public Trainer convertToEntity(TrainerDto trainerDto) {
        return Trainer.builder()
                .id(trainerDto.getId())
                .firstName(trainerDto.getFirstName())
                .lastName(trainerDto.getLastName())
                .username(trainerDto.getUsername())
                .password(trainerDto.getPassword())
                .isActive(trainerDto.isActive())
                .specialization(trainerDto.getSpecialization())
                .build();
    }
}
