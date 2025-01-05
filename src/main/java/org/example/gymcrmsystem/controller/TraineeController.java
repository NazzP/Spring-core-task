package org.example.gymcrmsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.facade.TraineeFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainees")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeFacade traineeFacade;

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
