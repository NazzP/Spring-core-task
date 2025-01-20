package org.example.gymcrmsystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.entity.User;
import org.example.gymcrmsystem.repository.UserRepository;
import org.springframework.stereotype.Component;

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

    private final UserRepository userRepository;

    public UsernameGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
        LOGGER.info("UsernameGenerator initialized with UserRepository");
    }

    public String generateUniqueUsername(UserDto userDto) {
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        LOGGER.debug("Generating unique username for: {} {}", firstName, lastName);

        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        LOGGER.debug("Base username generated: {}", baseUsername);

        boolean exists = checkIfUsernameExists(username);
        LOGGER.debug("Username '{}' existence check: {}", username, exists);

        while (exists) {
            username = baseUsername + suffix;
            LOGGER.debug("Username '{}' already exists, trying '{}'", baseUsername, username);
            suffix++;
            exists = checkIfUsernameExists(username);
        }

        LOGGER.info("Generated unique username: {}", username);
        return username;
    }

    boolean checkIfUsernameExists(String username) {
        LOGGER.debug("Checking if username '{}' exists in UserRepository", username);
        return userRepository.existsByUsername(username);
    }
}

