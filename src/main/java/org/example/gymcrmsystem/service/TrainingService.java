package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TrainingDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

public interface TrainingService {

    /**
     * Creates a new Training session.
     * <p>
     * This method accepts a {@link TrainingDto} object containing the details of the new Training session and passes it to the service layer
     * to handle the creation of the session.
     * <p>
     * @param trainingDTO - an object containing the details of the new Training session
     * @return The created {@link TrainingDto} with the assigned ID and relevant information
     * @throws NullEntityReferenceException - if the provided {@link TrainingDto} is null
     */
    TrainingDto create(TrainingDto trainingDTO) throws NullEntityReferenceException;

    /**
     * Retrieves a Training session by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainingDto} of the Training session with the given ID.
     * <p>
     * @param id - the unique ID of the Training session to be retrieved
     * @return The {@link TrainingDto} containing the Training session's information
     * @throws EntityNotFoundException - if no Training session with the given ID is found
     */
    TrainingDto select(Long id) throws EntityNotFoundException;
}
