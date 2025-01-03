package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainee;
import org.example.gymcrmsystem.utils.UsernameGenerator;

import java.util.Optional;

public interface TraineeRepository extends UserRepository {

    /**
     * Saves a Trainee entity to the data storage.
     * <p>
     * This method is used to persist a new Trainee entity or update an existing one in the storage.
     * <p>
     * @param trainee - the {@link Trainee} entity to be saved or updated
     * @return The saved {@link Trainee} entity with updated information
     */
    Trainee save(Trainee trainee);

    /**
     * Finds a Trainee entity by its unique ID.
     * <p>
     * This method searches for a Trainee entity in the storage using the given ID and returns it if found.
     * <p>
     * @param id - the unique identifier of the Trainee entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainee} entity if found, or empty if no entity is found
     */
    Optional<Trainee> findById(Long id);

    /**
     * Deletes a Trainee entity by its unique ID.
     * <p>
     * This method removes the Trainee entity with the specified ID from the storage.
     * <p>
     * @param id - the unique identifier of the Trainee entity to be deleted
     */
    void deleteById(Long id);

    /**
     * Checks if a Trainee entity exists with the given username.
     * <p>
     * This method verifies whether a Trainee with the specified username
     * is present in the storage. The comparison is case-sensitive.
     * <p>
     * This method is used in the {@link UsernameGenerator} class to directly check for the existence
     * of a username in the {@link TraineeRepository}.
     * <p>
     * @param username - the username of the Trainee to check for existence
     * @return {@code true} if a Trainer with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);
}