package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullEntityReferenceException;
import org.example.gymcrmsystem.exception.EntityNotFoundException;

public interface TrainerService {

    /**
     * Creates a new Trainer.
     * <p>
     * This method accepts a {@link TrainerDto} object containing the details of the new Trainer and passes it to the service layer
     * to handle the creation of the Trainer.
     * <p>
     * @param trainerDTO - an object containing the details of the new Trainer
     * @return The created {@link TrainerDto} with the assigned ID and relevant information
     * @throws NullEntityReferenceException - if the provided {@link TrainerDto} is null
     */
    TrainerDto create(TrainerDto trainerDTO) throws NullEntityReferenceException;

    /**
     * Retrieves a Trainer by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainerDto} of the Trainer with the given ID.
     * <p>
     * @param id - the unique ID of the Trainer to be retrieved
     * @return The {@link TrainerDto} containing the Trainer's information
     * @throws EntityNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto select(Long id) throws EntityNotFoundException;

    /**
     * Updates an existing Trainer.
     * <p>
     * This method updates the details of the Trainer identified by the given ID with the provided {@link TrainerDto}.
     * <p>
     * @param id         - the unique ID of the Trainer to be updated
     * @param trainerDTO - an object containing the updated information for the Trainer
     * @return The updated {@link TrainerDto} of the Trainer
     * @throws EntityNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto update(Long id, TrainerDto trainerDTO) throws EntityNotFoundException;
}
