package org.example.gymcrmsystem.controller;

import org.example.gymcrmsystem.dto.TraineeDTO;
import org.example.gymcrmsystem.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping()
    public ResponseEntity<TraineeDTO> createTrainee(@RequestBody TraineeDTO traineeDTO) {
        return new ResponseEntity<>(traineeService.create(traineeDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> selectTrainee(@PathVariable("id") Long id) {
        return new ResponseEntity<>(traineeService.select(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TraineeDTO> updateTrainee(@PathVariable("id") Long id, @RequestBody TraineeDTO traineeDTO) {
        return new ResponseEntity<>(traineeService.update(id, traineeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainee(@PathVariable("id") Long id) {
        traineeService.delete(id);
    }
}