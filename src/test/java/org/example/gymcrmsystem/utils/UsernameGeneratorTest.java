package org.example.gymcrmsystem.utils;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UsernameGeneratorTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    private UsernameGenerator usernameGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usernameGenerator = new UsernameGenerator(trainerRepository, traineeRepository);
    }

    @Test
    void generateUniqueUsernameForTrainee() {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("FirstName");
        traineeDto.setLastName("LastName");

        when(traineeRepository.existsByUsername("FirstName.LastName")).thenReturn(false);
        when(traineeRepository.existsByUsername("FirstName.LastName1")).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(traineeDto);

        assertEquals("FirstName.LastName", username);
    }

    @Test
    void generateUniqueUsernameWithSuffix() {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("FirstName");
        traineeDto.setLastName("LastName");

        when(traineeRepository.existsByUsername("FirstName.LastName")).thenReturn(true);
        when(traineeRepository.existsByUsername("FirstName.LastName")).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(traineeDto);

        assertEquals("FirstName.LastName", username);
    }

    @Test
    void generateUniqueUsernameForTrainer() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("Name");
        trainerDto.setLastName("Surname");

        when(trainerRepository.existsByUsername("Name.Surname")).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(trainerDto);

        assertEquals("Name.Surname", username);
    }

    @Test
    void generateUniqueUsernameForTrainerWithSuffix() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("Name");
        trainerDto.setLastName("Surname");

        when(trainerRepository.existsByUsername("Name.Surname")).thenReturn(true);
        when(trainerRepository.existsByUsername("Name.Surname1")).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(trainerDto);
        assertEquals("Name.Surname1", username);
    }

    @Test
    void checkIfUsernamesTakesFromAllStorages() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("Name");
        trainerDto.setLastName("Surname");

        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("Name");
        traineeDto.setLastName("Surname");

        when(trainerRepository.existsByUsername("Name.Surname")).thenReturn(true);
        when(trainerRepository.existsByUsername("Name.Surname1")).thenReturn(true);

        usernameGenerator.generateUniqueUsername(trainerDto);
        String username2 = usernameGenerator.generateUniqueUsername(traineeDto);
        assertEquals("Name.Surname2", username2);
    }

    @Test
    void generateUniqueUsernameForUnsupportedEntityType() {
        Object invalidEntity = new Object();
        assertThrows(IllegalArgumentException.class, () -> usernameGenerator.generateUniqueUsername(invalidEntity));
    }

}