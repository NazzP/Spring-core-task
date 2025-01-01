package org.example.gymcrmsystem.utils;

import org.example.gymcrmsystem.dto.input.TraineeInputDto;
import org.example.gymcrmsystem.dto.input.TrainerInputDto;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class UsernameGenerator {

    private final Map<Class<?>, Object> repositoryRegistry;

    public UsernameGenerator(TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        repositoryRegistry = new HashMap<>();
        repositoryRegistry.put(TrainerInputDto.class, trainerRepository);
        repositoryRegistry.put(TraineeInputDto.class, traineeRepository);
    }

    public <T> String generateUniqueUsername(T entity) {
        String firstName = extractFirstName(entity);
        String lastName = extractLastName(entity);

        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        Object repository = repositoryRegistry.get(entity.getClass());
        if (repository == null) {
            throw new IllegalArgumentException("No repository found for entity type " + entity.getClass().getSimpleName());
        }

        boolean exists = checkIfUsernameExists(repository, username);

        while (exists) {
            username = baseUsername + suffix;
            suffix++;
            exists = checkIfUsernameExists(repository, username);
        }

        return username;
    }

    private <T> String extractFirstName(T entity) {
        return extractName(entity, "getFirstName");
    }

    private <T> String extractLastName(T entity) {
        return extractName(entity, "getLastName");
    }

    private <T> String extractName(T entity, String methodName) {
        try {
            Method method = entity.getClass().getMethod(methodName);
            return (String) method.invoke(entity);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error extracting name from entity: " + e.getMessage());
        }
    }

    private boolean checkIfUsernameExists(Object repository, String username) {
        try {
            Method method = repository.getClass().getMethod("existsByUsername", String.class);
            return (boolean) method.invoke(repository, username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error checking username existence: " + e.getMessage());
        }
    }
}


