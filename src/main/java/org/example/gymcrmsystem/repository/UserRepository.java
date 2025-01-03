package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.utils.UsernameGenerator;

public interface UserRepository {

    /**
     * Checks if a User entity exists with the given username.
     * <p>
     * This method verifies whether a User (either a {@link Trainee} or {@link Trainer}) with the specified username
     * is present in the storage. The comparison is case-sensitive.
     * <p>
     * This method is used in the {@link UsernameGenerator} class to directly check for the existence
     * of a username in the respective repository (either {@link TrainerRepository} or {@link TraineeRepository}).
     * <p>
     * @param username - the username of the User to check for existence
     * @return {@code true} if a User with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);
}
