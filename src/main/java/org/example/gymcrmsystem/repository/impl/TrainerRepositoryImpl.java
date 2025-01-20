package org.example.gymcrmsystem.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.gymcrmsystem.entity.Trainer;
import org.example.gymcrmsystem.entity.User;
import org.example.gymcrmsystem.repository.TrainerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainerRepositoryImpl implements TrainerRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Trainer save(Trainer trainer) {
        if (trainer.getId() == null) {
            entityManager.persist(trainer);
        } else {
            entityManager.merge(trainer);
        }
        return trainer;
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = cb.createQuery(Trainer.class);
        Root<Trainer> root = query.from(Trainer.class);
        Join<Trainer, User> userJoin = root.join("user");
        query.select(root).where(cb.equal(userJoin.get("username"), username));

        try {
            Trainer result = entityManager.createQuery(query).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Trainer> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
