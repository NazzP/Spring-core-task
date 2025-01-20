package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

import java.util.List;

/**
 * Interface for managing operations related to trainers.
 * This interface provides methods for creating, retrieving, and updating trainer information.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the trainer data.
 * <p>
 *
 * @see TrainerDto
 */
public interface TrainerFacade {

    /**
     * Creates a new trainer record.
     * <p>
     * This method accepts a {@link TrainerDto} object containing the details of the new trainer.
     * It passes the DTO to the service layer to handle the actual creation of the trainer.
     * <p>
     *
     * @param trainerDto A {@link TrainerDto} object containing the data for the new trainer.
     * @return The created {@link TrainerDto} with the assigned ID and any other relevant information.
     */
    TrainerDto createTrainer(TrainerDto trainerDto) throws EntityNotFoundException;

    /**
     * Retrieves a trainer's information by their unique username.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainerDto} of the trainer with the provided username.
     * <p>
     *
     * @param username The unique username of the trainer to be retrieved.
     * @return The {@link TrainerDto} containing the trainer's information.
     */
    TrainerDto getTrainerByUsername(String username) throws EntityNotFoundException;

    /**
     * Updates an existing trainer's information.
     * <p>
     * This method accepts the trainer's username and the updated details in the form of a {@link TrainerDto}.
     * It passes these to the service layer to update the corresponding trainer record.
     * <p>
     *
     * @param username   The unique username of the trainer to be updated.
     * @param trainerDto A {@link TrainerDto} containing the updated information for the trainer.
     * @return The updated {@link TrainerDto} with the new details.
     */
    TrainerDto updateTrainer(String username, TrainerDto trainerDto) throws EntityNotFoundException;

    /**
     * Authenticates a trainer using their credentials.
     * <p>
     *
     * @param username The unique Username of the trainer.
     * @param password Trainer's password
     * @return A Boolean value indicating whether the authentication was successful.
     */
    boolean authenticateTrainer(String username, String password) throws EntityNotFoundException;

    /**
     * Changes the active status of a trainer.
     * <p>
     * This method allows toggling the active status of a trainer by their username.
     * <p>
     *
     * @param username The unique username of the trainer whose status is to be changed.
     * @param isActive A Boolean value indicating the new active status of the trainer.
     */
    void changeTrainerStatus(String username, Boolean isActive) throws EntityNotFoundException;

    /**
     * Changes the password of a trainer.
     * <p>
     * This method accepts a {@link TrainerDto} and a new password, and passes these to the service layer
     * to update the trainer's password.
     * <p>
     *
     * @param username     Unique Trainer's username
     * @param lastPassword The last password that was set for Trainer.
     * @param newPassword  The new password for the trainer.
     */
    void changeTrainerPassword(String username, String lastPassword, String newPassword) throws EntityNotFoundException;

    /**
     * Retrieves a list of unassigned trainers.
     * <p>
     * This method queries the service layer to get a list of trainers who are not currently assigned to a trainee.
     * <p>
     *
     * @param traineeUsername The username of the trainee for whom to retrieve unassigned trainers.
     * @return A list of {@link TrainerDto} objects representing unassigned trainers.
     */
    List<TrainerDto> getUnassignedTrainers(String traineeUsername) throws EntityNotFoundException;

    /**
     * Updates the list of trainers assigned to a trainee.
     * <p>
     * This method accepts the username of a trainee and a list of trainer usernames, and updates the trainee's
     * list of trainers in the service layer.
     * <p>
     *
     * @param traineeUsername   The username of the trainee whose trainer list is to be updated.
     * @param trainersUsernames A list of trainer usernames to be assigned to the trainee.
     * @return A list of {@link TrainerDto} objects representing the updated list of trainers.
     */
    List<TrainerDto> updateTrainersList(String traineeUsername, List<String> trainersUsernames) throws EntityNotFoundException;
}

