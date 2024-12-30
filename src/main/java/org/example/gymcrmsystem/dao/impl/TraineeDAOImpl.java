package org.example.gymcrmsystem.dao.impl;

import org.example.gymcrmsystem.dao.TraineeDAO;
import org.example.gymcrmsystem.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TraineeDAOImpl implements TraineeDAO {

    private final Map<Long, Trainee> traineeStorage;
    private final AtomicLong idCounterTrainee;

    @Autowired
    public TraineeDAOImpl(Map<Long, Trainee> traineeStorage, AtomicLong idCounterTrainee) {
        this.traineeStorage = traineeStorage;
        this.idCounterTrainee = idCounterTrainee;
    }

    @Override
    public Trainee save(Trainee trainee) {
        if (trainee.getId() == null) {
            trainee.setId(idCounterTrainee.getAndIncrement());
        }
        traineeStorage.put(trainee.getId(), trainee);
        return traineeStorage.get(trainee.getId());
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public void deleteById(Long id) {
        traineeStorage.remove(id);
    }
}