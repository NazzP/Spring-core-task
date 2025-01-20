package org.example.gymcrmsystem.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.entity.TrainingType;
import org.example.gymcrmsystem.repository.TrainingTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<TrainingType> findByName(String trainingTypeName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> query = cb.createQuery(TrainingType.class);
        Root<TrainingType> root = query.from(TrainingType.class);
        query.select(root).where(cb.equal(root.get("trainingTypeName"), trainingTypeName));

        try {
            TrainingType result = entityManager.createQuery(query).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
