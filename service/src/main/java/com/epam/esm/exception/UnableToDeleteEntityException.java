package com.epam.esm.exception;

public class UnableToDeleteEntityException extends RuntimeException {
    public UnableToDeleteEntityException() {
    }

    public UnableToDeleteEntityException(String message) {
        super(message);
    }
}
