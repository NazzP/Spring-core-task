package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.entity.TrainingType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.example.gymcrmsystem.config.AppConfig;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class TrainingTypeRepositoryTest {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    void findByNameSuccess() {
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findByName("Yoga");
        assertTrue(foundTrainingType.isPresent());
        assertEquals("Yoga", foundTrainingType.get().getTrainingTypeName());
    }

    @Test
    void findByNameNoResult() {
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findByName("NonExistentType");
        assertFalse(foundTrainingType.isPresent());
    }

    @Test
    void findByNameNull() {
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findByName(null);
        assertFalse(foundTrainingType.isPresent());
    }
}
