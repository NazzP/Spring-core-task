package org.example.gymcrmsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.facade.TrainerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerFacade trainerFacade;

    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerDto trainerDTO) {
        return new ResponseEntity<>(trainerFacade.createTrainer(trainerDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(trainerFacade.getTrainerById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable("id") Long id, @RequestBody TrainerDto trainerDTO) {
        return new ResponseEntity<>(trainerFacade.updateTrainer(id, trainerDTO), HttpStatus.OK);
    }
}
