package org.example.gymcrmsystem.parser;

/**
 * An interface that defines a contract for entities to provide a unique identifier.
 * <p>
 * Classes implementing this interface must provide a method to retrieve their unique ID,
 * typically used for mapping, storage, or identification purposes.
 * </p>
 * This interface is particularly useful in generic scenarios where entities of various types
 * need to be processed in a consistent way, such as storing them in a map by their ID.
 * </p>
 */
 public interface Identifiable<ID> {

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return the unique identifier of the entity, or {@code null} if not assigned
     */
    ID getId();
}
