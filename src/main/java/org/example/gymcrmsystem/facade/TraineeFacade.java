package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TraineeDTO;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;

/**
 * Interface for managing operations related to trainees.
 * This interface provides methods for creating, retrieving, updating, and deleting trainee information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainee data.
 *
 * @see TraineeDTO
 */
public interface TraineeFacade {

    /**
     * Creates a new trainee record.
     *
     * This method accepts a {@link TraineeDTO} object representing the details of the new trainee.
     * It passes the DTO to the service layer to handle the actual creation of the trainee.
     *
     * @param traineeDTO A {@link TraineeDTO} object containing the data for the new trainee.
     * @return The created {@link TraineeDTO} with the assigned ID and any other relevant information.
     * @throws NullObjectReferenceException If the provided {@link TraineeDTO} is null.
     */
    TraineeDTO createTrainee(TraineeDTO traineeDTO);

    /**
     * Retrieves a trainee's information by their unique ID.
     *
     * This method queries the service layer to retrieve the {@link TraineeDTO} of the trainee with the provided ID.
     *
     * @param id The unique ID of the trainee to be retrieved.
     * @return The {@link TraineeDTO} containing the trainee's information.
     * @throws ObjectNotFoundException If no trainee with the given ID is found.
     */
    TraineeDTO getTraineeById(Long id);

    /**
     * Updates an existing trainee's information.
     *
     * This method accepts the trainee's ID and the updated details in the form of a {@link TraineeDTO}.
     * It passes these to the service layer to update the corresponding trainee record.
     *
     * @param id The unique ID of the trainee to be updated.
     * @param traineeDTO A {@link TraineeDTO} containing the updated information for the trainee.
     * @return The updated {@link TraineeDTO} with the new details.
     * @throws ObjectNotFoundException If no trainee with the given ID is found.
     */
    TraineeDTO updateTrainee(Long id, TraineeDTO traineeDTO);

    /**
     * Deletes a trainee's record by their unique ID.
     *
     * This method deletes the trainee from the system based on the provided ID.
     * It ensures that the record is removed from the database, and handles any necessary validation.
     *
     * @param id The unique ID of the trainee to be deleted.
     * @throws ObjectNotFoundException If no trainee with the given ID is found.
     */
    void deleteTrainee(Long id);
}
