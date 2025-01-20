package org.example.gymcrmsystem.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.example.gymcrmsystem.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        User sampleUser = User.builder()
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .password("password")
                .isActive(true)
                .build();

        entityManager.persist(sampleUser);
    }

    @Test
    void findByUsernameSuccess() {
        Optional<User> foundUser = userRepository.findByUsername("testUser");
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void findByUsernameNoResult() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistentUser");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByUsernameNull() {
        Optional<User> foundUser = userRepository.findByUsername(null);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void existsByUsernameTrue() {
        boolean exists = userRepository.existsByUsername("testUser");
        assertTrue(exists);
    }

    @Test
    void existsByUsernameFalse() {
        boolean exists = userRepository.existsByUsername("nonexistentUser");
        assertFalse(exists);
    }

    @Test
    void existsByUsernameNull() {
        boolean exists = userRepository.existsByUsername(null);
        assertFalse(exists);
    }
}
