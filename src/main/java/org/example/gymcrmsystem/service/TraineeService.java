package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TraineeDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

public interface TraineeService {

    /**
     * Creates a new Trainee.
     * <p>
     * This method accepts a {@link TraineeDto} object containing the details of the new Trainee and passes it to the service layer
     * to handle the creation of the Trainee.
     * <p>
     * @param traineeDTO - an object containing the details of the new Trainee
     * @return The created {@link TraineeDto} with the assigned ID and relevant information
     * @throws NullEntityReferenceException - if the provided {@link TraineeDto} is null
     */
    TraineeDto create(TraineeDto traineeDTO) throws NullEntityReferenceException;

    /**
     * Retrieves a Trainee by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TraineeDto} of the Trainee with the given ID.
     * <p>
     * @param id - the unique ID of the Trainee to be retrieved
     * @return The {@link TraineeDto} containing the Trainee's information
     * @throws EntityNotFoundException - if no Trainee with the given ID is found
     */
    TraineeDto select(Long id) throws EntityNotFoundException;

    /**
     * Updates an existing Trainee.
     * <p>
     * This method updates the details of the Trainee identified by the given ID with the provided {@link TraineeDto}.
     * <p>
     * @param id         - the unique ID of the Trainee to be updated
     * @param traineeDTO - an object containing the updated information for the Trainee
     * @return The updated {@link TraineeDto} of the Trainee
     * @throws EntityNotFoundException - if Trainee with the given ID wasn't found
     */
    TraineeDto update(Long id, TraineeDto traineeDTO) throws EntityNotFoundException;

    /**
     * Deletes a Trainee by its ID.
     * <p>
     * This method removes the Trainee identified by the given ID.
     * <p>
     * @param id - the unique ID of the Trainee to be deleted
     * @throws EntityNotFoundException - if no Trainee with the given ID is found
     */
    void delete(Long id) throws EntityNotFoundException;
}
