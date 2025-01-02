package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TraineeDto;

/**
 * Interface for managing operations related to trainees.
 * This interface provides methods for creating, retrieving, updating, and deleting trainee information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainee data.
 *
 * @see TraineeDto
 */
public interface TraineeFacade {

    /**
     * Creates a new trainee record.
     * <p>
     * This method accepts a {@link TraineeDto} object representing the details of the new trainee.
     * It passes the DTO to the service layer to handle the actual creation of the trainee.
     * <p>
     * @param traineeDto A {@link TraineeDto} object containing the data for the new trainee.
     * @return The created {@link TraineeDto} with the assigned ID and any other relevant information.
     */
    TraineeDto createTrainee(TraineeDto traineeDto);

    /**
     * Retrieves a trainee's information by their unique ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TraineeDto} of the trainee with the provided ID.
     * <p>
     * @param id The unique ID of the trainee to be retrieved.
     * @return The {@link TraineeDto} containing the trainee's information.
     */
    TraineeDto getTraineeById(Long id);

    /**
     * Updates an existing trainee's information.
     * <p>
     * This method accepts the trainee's ID and the updated details in the form of a {@link TraineeDto}.
     * It passes these to the service layer to update the corresponding trainee record.
     * <p>
     * @param id The unique ID of the trainee to be updated.
     * @param traineeDto A {@link TraineeDto} containing the updated information for the trainee.
     * @return The updated {@link TraineeDto} with the new details.
     */
    TraineeDto updateTrainee(Long id, TraineeDto traineeDto);

    /**
     * Deletes a trainee's record by their unique ID.
     * <p>
     * This method deletes the trainee from the system based on the provided ID.
     * It ensures that the record is removed from the database, and handles any necessary validation.
     * <p>
     * @param id The unique ID of the trainee to be deleted.
     */
    void deleteTrainee(Long id);
}
