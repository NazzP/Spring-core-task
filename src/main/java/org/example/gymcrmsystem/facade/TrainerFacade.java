package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;

/**
 * Interface for managing operations related to trainers.
 * This interface provides methods for creating, retrieving, and updating trainer information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainer data.
 *
 * @see TrainerDto
 */
public interface TrainerFacade {

    /**
     * Creates a new trainer record.
     *
     * This method accepts a {@link TrainerDto} object containing the details of the new trainer.
     * It passes the DTO to the service layer to handle the actual creation of the trainer.
     *
     * @param trainerDTO A {@link TrainerDto} object containing the data for the new trainer.
     * @return The created {@link TrainerDto} with the assigned ID and any other relevant information.
     * @throws NullObjectReferenceException If the provided {@link TrainerDto} is null.
     */
    TrainerDto createTrainer(TrainerDto trainerDTO);

    /**
     * Retrieves a trainer's information by their unique ID.
     *
     * This method queries the service layer to retrieve the {@link TrainerDto} of the trainer with the provided ID.
     *
     * @param id The unique ID of the trainer to be retrieved.
     * @return The {@link TrainerDto} containing the trainer's information.
     * @throws ObjectNotFoundException If no trainer with the given ID is found.
     */
    TrainerDto getTrainerById(Long id);

    /**
     * Updates an existing trainer's information.
     *
     * This method accepts the trainer's ID and the updated details in the form of a {@link TrainerDto}.
     * It passes these to the service layer to update the corresponding trainer record.
     *
     * @param id The unique ID of the trainer to be updated.
     * @param trainerDTO A {@link TrainerDto} containing the updated information for the trainer.
     * @return The updated {@link TrainerDto} with the new details.
     * @throws ObjectNotFoundException If no trainer with the given ID is found.
     */
    TrainerDto updateTrainer(Long id, TrainerDto trainerDTO);
}
