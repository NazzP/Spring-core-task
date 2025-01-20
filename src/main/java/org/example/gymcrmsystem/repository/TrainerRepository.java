package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {

    /**
     * Saves a Trainer entity to the data storage.
     * <p>
     * This method is used to persist a new Trainer entity or update an existing one in the storage.
     * <p>
     *
     * @param trainer - the {@link Trainer} entity to be saved or updated
     * @return The saved {@link Trainer} entity with updated information
     */
    Trainer save(Trainer trainer);

    /**
     * Finds a Trainer entity by its unique username.
     * <p>
     * This method searches for a Trainer entity in the storage using the given username and returns it if found.
     * <p>
     *
     * @param username - the unique Username of the Trainer entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainer} entity if found, or empty if no entity is found
     */
    Optional<Trainer> findByUsername(String username);

    /**
     * Finds all Trainer entities
     * <p>
     * This method searches for a Trainer entity in the storage using the given username and returns it if found.
     * <p>
     *
     * @return An {@link List} containing the {@link Trainer} entities
     */
    List<Trainer> findAll();
}
