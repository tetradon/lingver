package com.kotlart.lingver.exception;

/**
 * This exception should wrap {@link org.springframework.dao.DataIntegrityViolationException }
 * in order to provide more descriptive error message
 */
public class UniqueConstraintViolationException extends RuntimeException {
    public UniqueConstraintViolationException() {
    }

    public UniqueConstraintViolationException(String message) {
        super(message);
    }

    public UniqueConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
