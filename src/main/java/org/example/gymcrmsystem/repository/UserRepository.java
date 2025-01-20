package org.example.gymcrmsystem.repository;

import org.example.gymcrmsystem.entity.User;
import org.example.gymcrmsystem.utils.UsernameGenerator;

import java.util.Optional;

public interface UserRepository {

    /**
     * Checks if a User entity exists with the given username.
     * <p>
     * This method is used in the {@link UsernameGenerator} class to directly check for the existence
     * of a User with indicated username
     * <p>
     *
     * @param username - the username of the User to check for existence
     * @return {@code true} if a User with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Finds a User entity by its unique username.
     * <p>
     * This method searches for a User entity in the storage using the given username and returns it if found.
     * <p>
     *
     * @param username - the unique Username of the User entity to be retrieved
     * @return An {@link Optional} containing the {@link User} entity if found, or empty if no entity is found
     */
    Optional<User> findByUsername(String username);
}
