package org.example.gymcrmsystem.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException() { }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
