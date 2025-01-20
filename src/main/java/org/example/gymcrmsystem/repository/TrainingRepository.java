package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.entity.Training;
import org.example.gymcrmsystem.entity.TrainingType;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository {

    /**
     * Saves a Training entity to the data storage.
     * <p>
     * This method is used to persist a new Training entity or update an existing one in the storage.
     * <p>
     *
     * @param training - the {@link Training} entity to be saved or updated
     * @return The saved {@link Training} entity with updated information
     */
    Training save(Training training);

    /**
     * Finds a list of Training by using special criteria
     * <p>
     * This method is used to persist a new Training entity or update an existing one in the storage.
     * <p>
     *
     * @param traineeUsername - the {@link Trainee} Trainee username
     * @param fromDate        - the date from which we start
     * @param toDate          - date until which we continue the search
     * @param trainingType    - the {@link TrainingType} TrainingTypeName is using
     * @param trainerName     - the {@link Trainer} Trainer firstName
     * @return The saved {@link Training} entity with updated information
     */
    List<Training> getByTraineeCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                        String trainerName, String trainingType);


    /**
     * Finds a list of Training by using special criteria
     * <p>
     * This method is used to persist a new Training entity or update an existing one in the storage.
     * <p>
     *
     * @param trainerUsername - the {@link Trainer} Trainee username
     * @param fromDate        - the date from which we start
     * @param toDate          - date until which we continue the search
     * @param traineeName     - the {@link Trainee} Trainee firstName
     * @return The saved {@link Training} entity with updated information
     */
    List<Training> getByTrainerCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate,
                                        String traineeName);
}
