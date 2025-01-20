package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.entity.Trainee;

import java.util.Optional;

public interface TraineeRepository {

    /**
     * Saves a Trainee entity to the data storage.
     * <p>
     * This method is used to persist a new Trainee entity or update an existing one in the storage.
     * <p>
     *
     * @param trainee - the {@link Trainee} entity to be saved or updated
     * @return The saved {@link Trainee} entity with updated information
     */
    Trainee save(Trainee trainee);

    /**
     * Finds a Trainee entity by its unique username.
     * <p>
     * This method searches for a Trainee entity in the storage using the given username and returns it if found.
     * <p>
     *
     * @param username - the unique Username of the Trainee entity to be retrieved
     * @return An {@link Optional} containing the {@link Trainee} entity if found, or empty if no entity is found
     */
    Optional<Trainee> findByUsername(String username);

    /**
     * Deletes Trainee by its unique username.
     * <p>
     *
     * @param username - the unique Username of the Trainee entity to be retrieved
     */
    void deleteByUsername(String username);
}
