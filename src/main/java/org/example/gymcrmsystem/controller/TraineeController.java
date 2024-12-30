package org.example.gymcrmsystem.controller;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.facade.TraineeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    private final TraineeFacade traineeFacade;

    @Autowired
    public TraineeController(TraineeFacade traineeFacade) {
        this.traineeFacade = traineeFacade;
    }

    @PostMapping
    public ResponseEntity<TraineeDto> createTrainee(@RequestBody TraineeDto traineeDTO) {
        return new ResponseEntity<>(traineeFacade.createTrainee(traineeDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeDto> getTrainee(@PathVariable Long id) {
        return new ResponseEntity<>(traineeFacade.getTraineeById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TraineeDto> updateTrainee(@PathVariable Long id, @RequestBody TraineeDto traineeDTO) {
        return new ResponseEntity<>(traineeFacade.updateTrainee(id, traineeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        traineeFacade.deleteTrainee(id);
        return ResponseEntity.noContent().build();
    }
}