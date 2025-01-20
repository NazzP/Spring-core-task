package org.example.gymcrmsystem.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.entity.Training;
import org.example.gymcrmsystem.repository.TrainingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainingRepositoryImpl implements TrainingRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Training save(Training training) {
        if (training.getId() == null) {
            entityManager.persist(training);
        } else {
            entityManager.merge(training);
        }
        return training;
    }

    @Override
    public List<Training> getByTraineeCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                               String trainerName, String trainingType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> root = query.from(Training.class);

        Predicate[] predicates = new Predicate[5];
        predicates[0] = cb.equal(root.get("trainee").get("user").get("username"), traineeUsername);
        predicates[1] = cb.greaterThanOrEqualTo(root.get("date"), fromDate);
        predicates[2] = cb.lessThanOrEqualTo(root.get("date"), toDate);
        predicates[3] = cb.equal(root.get("trainer").get("user").get("firstName"), trainerName);
        predicates[4] = cb.equal(root.get("trainingType").get("trainingTypeName"), trainingType);

        query.select(root).where(predicates);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Training> getByTrainerCriteria(String trainerUsername, LocalDate fromDate, LocalDate toDate,
                                               String traineeName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> root = query.from(Training.class);

        Predicate[] predicates = new Predicate[4];
        predicates[0] = cb.equal(root.get("trainer").get("user").get("username"), trainerUsername);
        predicates[1] = cb.greaterThanOrEqualTo(root.get("date"), fromDate);
        predicates[2] = cb.lessThanOrEqualTo(root.get("date"), toDate);
        predicates[3] = cb.equal(root.get("trainee").get("user").get("firstName"), traineeName);

        query.select(root).where(predicates);
        return entityManager.createQuery(query).getResultList();
    }
}
