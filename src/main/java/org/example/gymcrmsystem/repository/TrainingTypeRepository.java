package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.entity.TrainingType;

import java.util.Optional;

public interface TrainingTypeRepository {

    /**
     * Finds a Training entity by its unique training_type_name.
     * <p>
     * This method searches for a TrainingType entity in the storage using the given training_type_name and returns it if found.
     * <p>
     *
     * @param name - the unique training_type_name of the TrainingType entity to be retrieved
     * @return An {@link Optional} containing the {@link TrainingType} entity if found, or empty if no entity is found
     */
    Optional<TrainingType> findByName(String name);
}
