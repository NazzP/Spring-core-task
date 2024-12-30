package org.example.gymcrmsystem.service;

import org.example.gymcrmsystem.dto.TrainerDto;
import org.example.gymcrmsystem.exception.NullObjectReferenceException;
import org.example.gymcrmsystem.exception.ObjectNotFoundException;

public interface TrainerService {

    /**
     * Creates a new Trainer.
     * <p>
     * This method accepts a {@link TrainerDto} object containing the details of the new Trainer and passes it to the service layer
     * to handle the creation of the Trainer.
     *
     * @param trainerDTO - an object containing the details of the new Trainer
     * @return The created {@link TrainerDto} with the assigned ID and relevant information
     * @throws NullObjectReferenceException - if the provided {@link TrainerDto} is null
     */
    TrainerDto create(TrainerDto trainerDTO);

    /**
     * Retrieves a Trainer by its ID.
     * <p>
     * This method queries the service layer to retrieve the {@link TrainerDto} of the Trainer with the given ID.
     *
     * @param id - the unique ID of the Trainer to be retrieved
     * @return The {@link TrainerDto} containing the Trainer's information
     * @throws ObjectNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto select(Long id);

    /**
     * Updates an existing Trainer.
     * <p>
     * This method updates the details of the Trainer identified by the given ID with the provided {@link TrainerDto}.
     *
     * @param id         - the unique ID of the Trainer to be updated
     * @param trainerDTO - an object containing the updated information for the Trainer
     * @return The updated {@link TrainerDto} of the Trainer
     * @throws ObjectNotFoundException - if no Trainer with the given ID is found
     */
    TrainerDto update(Long id, TrainerDto trainerDTO);
}