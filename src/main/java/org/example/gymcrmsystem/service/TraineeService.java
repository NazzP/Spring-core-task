package org.example.gymcrmsystem.service;

import jakarta.validation.Valid;
import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

public interface TraineeService {

    /**
     * Creates a new Trainee.
     * <p>
     * This method accepts a {@link TraineeDto} object containing the details of the new Trainee and passes it to the service layer
     * to handle the creation of the Trainee.
     * <p>
     *
     * @param traineeDTO - an object containing the details of the new Trainee
     * @return The created {@link TraineeDto} with the assigned ID and relevant information
     */
    TraineeDto create(@Valid TraineeDto traineeDTO);

    /**
     * Retrieves a Trainee by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TraineeDto} of the Trainee with the given ID.
     * <p>
     *
     * @param username - the unique Username of the Trainee to be retrieved
     * @return The {@link TraineeDto} containing the Trainee's information
     * @throws EntityNotFoundException - if no Trainee with the given ID is found
     */
    TraineeDto select(String username) throws EntityNotFoundException;

    /**
     * Updates an existing Trainee.
     * <p>
     * This method updates the details of the Trainee identified by the given ID with the provided {@link TraineeDto}.
     * <p>
     *
     * @param username   - the unique Username of the Trainee to be updated
     * @param traineeDTO - an object containing the updated information for the Trainee
     * @return The updated {@link TraineeDto} of the Trainee
     * @throws EntityNotFoundException - if Trainee with the given ID wasn't found
     */
    TraineeDto update(String username, @Valid TraineeDto traineeDTO) throws EntityNotFoundException;

    /**
     * Deletes a Trainee by its ID.
     * <p>
     * This method removes the Trainee identified by the given ID.
     * <p>
     *
     * @param username - the unique Username of the Trainee to be deleted
     * @throws EntityNotFoundException - if no Trainee with the given ID is found
     */
    void delete(String username) throws EntityNotFoundException;

    /**
     * Changes the active status of a Trainee.
     * <p>
     * This method updates the "isActive" status of a Trainee's account based on the provided username.
     * It first retrieves the Trainee using the username, then modifies the status to either active or inactive.
     * The updated Trainee entity is saved in the repository.
     * <p>
     *
     * @param username The unique username of the Trainee whose status is to be updated.
     * @param isActive A Boolean indicating the new status of the Trainee. `true` for active, `false` for inactive.
     */
    void changeStatus(String username, Boolean isActive) throws EntityNotFoundException;

    /**
     * Authenticates a Trainee based on their credentials.
     * <p>
     * This method checks if the provided password for the given Trainee matches the one stored in the system.
     * It first retrieves the Trainee by their username, and then compares the provided password
     * with the stored password using a password encoder.
     * <p>
     *
     * @param username The unique Username of the trainee.
     * @param password Trainee's password
     * @return A boolean indicating whether the provided password matches the stored password. Returns `true` if they match, otherwise `false`.
     */
    boolean authenticateTrainee(String username, String password) throws EntityNotFoundException;

    /**
     * Changes the password of a Trainee.
     * <p>
     *
     * @param username     Unique Trainee's username
     * @param lastPassword The last password that was set for Trainee.
     * @param newPassword  The new password to be set for the Trainee.
     */
    void changePassword(String username, String lastPassword, String newPassword) throws EntityNotFoundException, IllegalArgumentException;

    /**
     * Handles the process of initiating a password reset for a user.
     *
     * @param username the username of the user requesting a password reset.
     * @return a confirmation message indicating that the password reset process
     * has been initiated (e.g., a message indicating an email has been sent).
     * @throws EntityNotFoundException if no user is found with the provided username.
     */
    String forgotPassword(String username) throws EntityNotFoundException;
}
