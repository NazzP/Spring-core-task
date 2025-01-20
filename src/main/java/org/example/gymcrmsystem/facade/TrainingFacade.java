package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing operations related to training sessions.
 * This interface provides methods for creating and retrieving training sessions.
 * It serves as a facade to the underlying service layer, abstracting the complexity of direct interactions with data access layers.
 * All operations return DTO (Data Transfer Object) representations of the training session data.
 *
 * @see TrainingDto
 */
public interface TrainingFacade {

    /**
     * Adds a new training session.
     *
     * @param trainingDto A {@link TrainingDto} object containing the details of the new training session.
     * @return The created {@link TrainingDto} with the assigned ID and relevant information.
     */
    TrainingDto addTraining(TrainingDto trainingDto) throws EntityNotFoundException;

    /**
     * Retrieves a list of training sessions for a specific trainee.
     *
     * @param traineeUsername The username of the trainee whose training sessions are to be retrieved.
     * @param fromDate        The start date for filtering training sessions.
     * @param toDate          The end date for filtering training sessions.
     * @param trainingType    The type of training sessions to filter by.
     * @return A list of {@link TrainingDto} objects representing the trainee's training sessions.
     */
    List<TrainingDto> getTraineeTrainings(String traineeUsername, LocalDate fromDate,
                                          LocalDate toDate, String traineeName, String trainingType) throws EntityNotFoundException;

    /**
     * Retrieves a list of training sessions for a specific trainer.
     *
     * @param trainerUsername The username of the trainer whose training sessions are to be retrieved.
     * @param fromDate        The start date for filtering training sessions.
     * @param toDate          The end date for filtering training sessions.
     * @param trainerName     The firstName of Trainer whose training sessions are to be retrieved.
     * @return A list of {@link TrainingDto} objects representing the trainer's training sessions.
     */
    List<TrainingDto> getTrainerTrainings(String trainerUsername, LocalDate fromDate,
                                          LocalDate toDate, String trainerName) throws EntityNotFoundException;
}

