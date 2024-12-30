package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainer;

import java.util.Optional;

public interface TrainerRepository {

    /**
     * Saves a Trainer entity to the data storage.
     *
     * This method is used to persist a new Trainer entity or update an existing one in the storage.
     *
     * @param trainer - the {@link Trainer} entity to be saved or updated
     * @return The saved {@link Trainer} entity with updated information
     */
    Trainer save(Trainer trainer);

    /**
     * Finds a Trainer entity by its unique ID.
     *
     * This method searches for a Trainer entity in the storage using the given ID and returns it if found.
     *
     * @param id - the unique identifier of the Trainer entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainer} entity if found, or empty if no entity is found
     */
    Optional<Trainer> findById(Long id);
}