package com.smartcampus.exceptions;

/**
 * Custom runtime exception used when an entity tries to reference a foreign key (like Room ID)
 * that does not actually exist in the system.
 */
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}
