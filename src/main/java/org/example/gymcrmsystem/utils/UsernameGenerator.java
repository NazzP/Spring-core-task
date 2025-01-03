package org.example.gymcrmsystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.model.User;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.example.gymcrmsystem.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This component is responsible for generating unique usernames for classes which extends {@link User} entities.
 * <p>
 * The class creates a username by combining the first and last name of the provided entity. If the generated username
 * already exists in the repository, it appends a numeric suffix (e.g., "FirstName.LastName", "FirstName.LastName")
 * to ensure the username is unique.
 * The class supports both {@link TrainerDto} and {@link TraineeDto} types.
 * </p>
 * The class uses the repository registry to check the existence of the username across all registered repositories.
 * The repositories for {@link TrainerDto} and {@link TraineeDto} are injected at the constructor.
 * </p>
 * If u want to extend the usage on more entities - add needed class to the constructor. Also, ensure that u have
 * redefined ( override ) the method existsByUsername from {@link UserRepository}
 */
@Slf4j
@Component
public class UsernameGenerator {

    private final Map<Class<?>, UserRepository> repositoryRegistry;

    public UsernameGenerator(TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        repositoryRegistry = new HashMap<>();
        repositoryRegistry.put(TrainerDto.class, trainerRepository);
        repositoryRegistry.put(TraineeDto.class, traineeRepository);
        log.info("UsernameGenerator initialized with repositories: TrainerRepository and TraineeRepository");
    }

    public <T> String generateUniqueUsername(T entity) {
        log.debug("Generating unique username for entity: {}", entity);

        String firstName = extractFirstName(entity);
        String lastName = extractLastName(entity);

        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        log.debug("Base username generated: {}", baseUsername);

        boolean exists = checkIfUsernameExists(username);
        log.debug("Username '{}' existence check: {}", username, exists);

        while (exists) {
            username = baseUsername + suffix;
            log.debug("Username '{}' already exists, trying '{}'", baseUsername, username);
            suffix++;
            exists = checkIfUsernameExists(username);
        }

        log.info("Generated unique username: {}", username);
        return username;
    }

    private <T> String extractFirstName(T entity) {
        if (entity instanceof TrainerDto trainerDto) {
            log.debug("Extracting first name for TrainerDto: {}", trainerDto.getFirstName());
            return trainerDto.getFirstName();
        } else if (entity instanceof TraineeDto traineeDto) {
            log.debug("Extracting first name for TraineeDto: {}", traineeDto.getFirstName());
            return traineeDto.getFirstName();
        }
        log.warn("Unsupported entity type for first name extraction: {}", entity.getClass());
        throw new IllegalArgumentException("Unsupported entity type");
    }

    private <T> String extractLastName(T entity) {
        if (entity instanceof TrainerDto trainerDto) {
            log.debug("Extracting last name for TrainerDto: {}", trainerDto.getLastName());
            return trainerDto.getLastName();
        } else if (entity instanceof TraineeDto traineeDto) {
            log.debug("Extracting last name for TraineeDto: {}", traineeDto.getLastName());
            return traineeDto.getLastName();
        }
        log.warn("Unsupported entity type for last name extraction: {}", entity.getClass());
        throw new IllegalArgumentException("Unsupported entity type");
    }

    private boolean checkIfUsernameExists(String username) {
        log.debug("Checking if username '{}' exists in any repository", username);
        for (UserRepository repository : repositoryRegistry.values()) {
            if (repository.existsByUsername(username)) {
                log.debug("Username '{}' exists in repository: {}", username, repository.getClass().getSimpleName());
                return true;
            }
        }
        log.debug("Username '{}' does not exist in any repository", username);
        return false;
    }
}
