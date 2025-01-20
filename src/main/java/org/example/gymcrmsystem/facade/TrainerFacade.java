package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainerDto;

/**
 * Interface for managing operations related to trainers.
 * This interface provides methods for creating, retrieving, and updating trainer information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainer data.
 * <p>
 * @see TrainerDto
 */
public interface TrainerFacade {

    /**
     * Creates a new trainer record.
     * <p>
     * This method accepts a {@link TrainerDto} object containing the details of the new trainer.
     * It passes the DTO to the service layer to handle the actual creation of the trainer.
     * <p>
     * @param trainerDto A {@link TrainerDto} object containing the data for the new trainer.
     * @return The created {@link TrainerDto} with the assigned ID and any other relevant information.
     */
    TrainerDto createTrainer(TrainerDto trainerDto);

    /**
     * Retrieves a trainer's information by their unique ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainerDto} of the trainer with the provided ID.
     * <p>
     * @param id The unique ID of the trainer to be retrieved.
     * @return The {@link TrainerDto} containing the trainer's information.
     */
    TrainerDto getTrainerById(Long id);

    /**
     * Updates an existing trainer's information.
     * <p>
     * This method accepts the trainer's ID and the updated details in the form of a {@link TrainerDto}.
     * It passes these to the service layer to update the corresponding trainer record.
     * <p>
     * @param id The unique ID of the trainer to be updated.
     * @param trainerDto A {@link TrainerDto} containing the updated information for the trainer.
     * @return The updated {@link TrainerDto} with the new details.
     */
    TrainerDto updateTrainer(Long id, TrainerDto trainerDto);
}
