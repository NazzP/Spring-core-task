package org.example.gymcrmsystem.utils;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UsernameGenerator {

    private final Map<Class<?>, UserRepository> repositoryRegistry;

    public UsernameGenerator(TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        repositoryRegistry = new HashMap<>();
        repositoryRegistry.put(TrainerDto.class, trainerRepository);
        repositoryRegistry.put(TraineeDto.class, traineeRepository);
    }

    public <T> String generateUniqueUsername(T entity) {
        String firstName = extractFirstName(entity);
        String lastName = extractLastName(entity);

        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        boolean exists = checkIfUsernameExists(username);

        while (exists) {
            username = baseUsername + suffix;
            suffix++;
            exists = checkIfUsernameExists(username);
        }

        return username;
    }

    private <T> String extractFirstName(T entity) {
        if (entity instanceof TrainerDto trainerDto) {
            return trainerDto.getFirstName();
        } else if (entity instanceof TraineeDto traineeDto) {
            return traineeDto.getFirstName();
        }
        throw new IllegalArgumentException("Unsupported entity type");
    }

    private <T> String extractLastName(T entity) {
        if (entity instanceof TrainerDto trainerDto) {
            return trainerDto.getLastName();
        } else if (entity instanceof TraineeDto traineeDto) {
            return traineeDto.getLastName();
        }
        throw new IllegalArgumentException("Unsupported entity type");
    }

    private boolean checkIfUsernameExists(String username) {
        for (UserRepository repository : repositoryRegistry.values()) {
            if (repository.existsByUsername(username)) {
                return true;
            }
        }
        return false;
    }
}
