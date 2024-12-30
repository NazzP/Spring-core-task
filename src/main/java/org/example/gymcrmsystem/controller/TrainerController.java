package org.example.gymcrmsystem.controller;

import org.example.gymcrmsystem.dto.TrainerDTO;
import org.example.gymcrmsystem.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping()
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerService.create(trainerDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> selectTrainer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(trainerService.select(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable("id") Long id, @RequestBody TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerService.update(id, trainerDTO), HttpStatus.OK);
    }
}