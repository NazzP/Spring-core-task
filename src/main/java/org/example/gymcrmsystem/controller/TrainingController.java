package org.example.gymcrmsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.facade.TrainingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingFacade trainingFacade;

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto trainingDTO) {
        return new ResponseEntity<>(trainingFacade.createTraining(trainingDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTraining(@PathVariable("id") Long id) {
        return new ResponseEntity<>(trainingFacade.getTrainingById(id), HttpStatus.OK);
    }
}
