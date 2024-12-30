package org.example.gymcrmsystem.exception;

public class NullObjectReferenceException extends RuntimeException {
    public NullObjectReferenceException() {    }

    public NullObjectReferenceException(String message) {
        super(message);
    }
}