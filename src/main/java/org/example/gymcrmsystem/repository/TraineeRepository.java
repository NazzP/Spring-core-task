package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.model.Trainee;

import java.util.Optional;

public interface TraineeRepository {

    /**
     * Saves a Trainee entity to the data storage.
     *
     * This method is used to persist a new Trainee entity or update an existing one in the storage.
     *
     * @param trainee - the {@link Trainee} entity to be saved or updated
     * @return The saved {@link Trainee} entity with updated information
     */
    Trainee save(Trainee trainee);

    /**
     * Finds a Trainee entity by its unique ID.
     *
     * This method searches for a Trainee entity in the storage using the given ID and returns it if found.
     *
     * @param id - the unique identifier of the Trainee entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainee} entity if found, or empty if no entity is found
     */
    Optional<Trainee> findById(Long id);

    /**
     * Deletes a Trainee entity by its unique ID.
     *
     * This method removes the Trainee entity with the specified ID from the storage.
     *
     * @param id - the unique identifier of the Trainee entity to be deleted
     */
    void deleteById(Long id);
}