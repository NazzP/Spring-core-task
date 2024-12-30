package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainerDTO;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;

/**
 * Interface for managing operations related to trainers.
 * This interface provides methods for creating, retrieving, and updating trainer information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainer data.
 *
 * @see TrainerDTO
 */
public interface TrainerFacade {

    /**
     * Creates a new trainer record.
     *
     * This method accepts a {@link TrainerDTO} object containing the details of the new trainer.
     * It passes the DTO to the service layer to handle the actual creation of the trainer.
     *
     * @param trainerDTO A {@link TrainerDTO} object containing the data for the new trainer.
     * @return The created {@link TrainerDTO} with the assigned ID and any other relevant information.
     * @throws NullObjectReferenceException If the provided {@link TrainerDTO} is null.
     */
    TrainerDTO createTrainer(TrainerDTO trainerDTO);

    /**
     * Retrieves a trainer's information by their unique ID.
     *
     * This method queries the service layer to retrieve the {@link TrainerDTO} of the trainer with the provided ID.
     *
     * @param id The unique ID of the trainer to be retrieved.
     * @return The {@link TrainerDTO} containing the trainer's information.
     * @throws ObjectNotFoundException If no trainer with the given ID is found.
     */
    TrainerDTO getTrainerById(Long id);

    /**
     * Updates an existing trainer's information.
     *
     * This method accepts the trainer's ID and the updated details in the form of a {@link TrainerDTO}.
     * It passes these to the service layer to update the corresponding trainer record.
     *
     * @param id The unique ID of the trainer to be updated.
     * @param trainerDTO A {@link TrainerDTO} containing the updated information for the trainer.
     * @return The updated {@link TrainerDTO} with the new details.
     * @throws ObjectNotFoundException If no trainer with the given ID is found.
     */
    TrainerDTO updateTrainer(Long id, TrainerDTO trainerDTO);
}
