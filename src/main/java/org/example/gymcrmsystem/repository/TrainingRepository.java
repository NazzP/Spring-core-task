package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Training;

import java.util.Optional;

public interface TrainingRepository {

    /**
     * Saves a Training entity to the data storage.
     * <p>
     * This method is used to persist a new Training entity or update an existing one in the storage.
     * <p>
     * @param training - the {@link Training} entity to be saved or updated
     * @return The saved {@link Training} entity with updated information
     */
    Training save(Training training);

    /**
     * Finds a Training entity by its unique ID.
     * <p>
     * This method searches for a Training entity in the storage using the given ID and returns it if found.
     * <p>
     * @param id - the unique identifier of the Training entity to be retrieved
     * @return An {@link Optional} containing the {@link Training} entity if found, or empty if no entity is found
     */
    Optional<Training> findById(Long id);
}
