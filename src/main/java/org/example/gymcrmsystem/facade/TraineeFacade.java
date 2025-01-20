package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

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
     *
     * @param traineeDto A {@link TraineeDto} object containing the data for the new trainee.
     * @return The created {@link TraineeDto} with the assigned ID and any other relevant information.
     */
    TraineeDto createTrainee(TraineeDto traineeDto);

    /**
     * Retrieves a trainee's information by their unique ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TraineeDto} of the trainee with the provided ID.
     * <p>
     *
     * @param username The unique Username of the trainee to be retrieved.
     * @return The {@link TraineeDto} containing the trainee's information.
     */
    TraineeDto getTraineeByUsername(String username) throws EntityNotFoundException;

    /**
     * Updates an existing trainee's information.
     * <p>
     * This method accepts the trainee's ID and the updated details in the form of a {@link TraineeDto}.
     * It passes these to the service layer to update the corresponding trainee record.
     * <p>
     *
     * @param username   The unique Username of the trainee to be updated.
     * @param traineeDto A {@link TraineeDto} containing the updated information for the trainee.
     * @return The updated {@link TraineeDto} with the new details.
     */
    TraineeDto updateTrainee(String username, TraineeDto traineeDto) throws EntityNotFoundException;

    /**
     * Deletes a trainee's record by their unique ID.
     * <p>
     * This method deletes the trainee from the system based on the provided ID.
     * It ensures that the record is removed from the database, and handles any necessary validation.
     * <p>
     *
     * @param username The unique Username of the trainee to be deleted.
     */
    void deleteTrainee(String username) throws EntityNotFoundException;

    /**
     * Changes the status (active or inactive) for a trainee.
     * <p>
     *
     * @param username The unique Username of the trainee.
     * @param isActive A Boolean value indicating whether the trainee is active.
     */
    void changeTraineeStatus(String username, Boolean isActive) throws EntityNotFoundException;

    /**
     * Authenticates a trainee using their credentials.
     * <p>
     *
     * @param username The unique Username of the trainee.
     * @param password Trainee's password
     * @return A Boolean value indicating whether the authentication was successful.
     */
    boolean authenticateTrainee(String username, String password) throws EntityNotFoundException;

    /**
     * Changes the password for a trainee.
     * <p>
     *
     * @param username     Unique Trainee's username
     * @param lastPassword The last password that was set for Trainee.
     * @param newPassword  The new password to be set for the Trainee.
     */
    void changeTraineePassword(String username, String lastPassword, String newPassword) throws EntityNotFoundException;
}
