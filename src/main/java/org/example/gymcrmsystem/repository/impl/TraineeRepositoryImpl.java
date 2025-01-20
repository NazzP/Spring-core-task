package org.example.gymcrmsystem.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.entity.Trainee;
import org.example.gymcrmsystem.repository.TraineeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TraineeRepositoryImpl implements TraineeRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Trainee save(Trainee trainee) {
        if (trainee.getId() == null) {
            entityManager.persist(trainee);
        } else {
            trainee = entityManager.merge(trainee);
        }
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> query = cb.createQuery(Trainee.class);
        Root<Trainee> root = query.from(Trainee.class);
        query.select(root).where(cb.equal(root.get("user").get("username"), username));

        try {
            Trainee result = entityManager.createQuery(query).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByUsername(String username) {
        findByUsername(username).ifPresent(entityManager::remove);
    }
}
