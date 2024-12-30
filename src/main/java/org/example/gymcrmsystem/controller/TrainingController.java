package org.example.gymcrmsystem.controller;

import org.example.gymcrmsystem.dto.TrainingDTO;
import org.example.gymcrmsystem.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping()
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody TrainingDTO trainingDTO) {
        return new ResponseEntity<>(trainingService.create(trainingDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> selectTraining(@PathVariable("id") Long id) {
        return new ResponseEntity<>(trainingService.select(id), HttpStatus.OK);
    }
}