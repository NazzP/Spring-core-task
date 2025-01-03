package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainer;
import org.example.gymcrmsystem.utils.UsernameGenerator;

import java.util.Optional;

public interface TrainerRepository extends UserRepository {

    /**
     * Saves a Trainer entity to the data storage.
     * <p>
     * This method is used to persist a new Trainer entity or update an existing one in the storage.
     * <p>
     * @param trainer - the {@link Trainer} entity to be saved or updated
     * @return The saved {@link Trainer} entity with updated information
     */
    Trainer save(Trainer trainer);

    /**
     * Finds a Trainer entity by its unique ID.
     * <p>
     * This method searches for a Trainer entity in the storage using the given ID and returns it if found.
     * <p>
     * @param id - the unique identifier of the Trainer entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainer} entity if found, or empty if no entity is found
     */
    Optional<Trainer> findById(Long id);

    /**
     * Checks if a Trainer entity exists with the given username.
     * <p>
     * This method verifies whether a Trainer with the specified username
     * is present in the storage. The comparison is case-sensitive.
     * <p>
     * This method is used in the {@link UsernameGenerator} class to directly check for the existence
     * of a username in the {@link TrainerRepository}.
     * <p>
     * @param username - the username of the Trainer to check for existence
     * @return {@code true} if a Trainer with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);
}