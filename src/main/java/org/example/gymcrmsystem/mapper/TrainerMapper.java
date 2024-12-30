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

    public Trainer convertToEntity(TrainerDto trainerDTO) {
        return Trainer.builder()
                .id(trainerDTO.getId())
                .firstName(trainerDTO.getFirstName())
                .lastName(trainerDTO.getLastName())
                .username(trainerDTO.getUsername())
                .password(trainerDTO.getPassword())
                .isActive(trainerDTO.isActive())
                .specialization(trainerDTO.getSpecialization())
                .build();
    }
}
