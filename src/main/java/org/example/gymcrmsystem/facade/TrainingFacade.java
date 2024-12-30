package org.example.gymcrmsystem.facade;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;

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
     * Creates a new training session.
     *
     * This method accepts a {@link TrainingDto} object containing the details of the new training session.
     * It passes the DTO to the service layer to handle the actual creation of the training.
     *
     * @param trainingDto A {@link TrainingDto} object containing the data for the new training session.
     * @return The created {@link TrainingDto} with the assigned ID and any other relevant information.
     * @throws NullObjectReferenceException If the provided {@link TrainingDto} is null.
     */
    TrainingDto createTraining(TrainingDto trainingDto);

    /**
     * Retrieves a training session's information by its unique ID.
     *
     * This method queries the service layer to retrieve the {@link TrainingDto} of the training session with the provided ID.
     *
     * @param id The unique ID of the training session to be retrieved.
     * @return The {@link TrainingDto} containing the training session's information.
     * @throws ObjectNotFoundException If no training session with the given ID is found.
     */
    TrainingDto getTrainingById(Long id);
}
