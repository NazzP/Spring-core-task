package org.example.gymcrmsystem.controller;

import org.example.gymcrmsystem.dto.TrainerDTO;
import org.example.gymcrmsystem.facade.TrainerFacade;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerFacade trainerFacade;

    @Autowired
    public TrainerController(TrainerFacade trainerFacade) {
        this.trainerFacade = trainerFacade;
    }

    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerFacade.createTrainer(trainerDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(trainerFacade.getTrainerById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable("id") Long id, @RequestBody TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerFacade.updateTrainer(id, trainerDTO), HttpStatus.OK);
    }
}