package com.kotlart.lingver.exception;

/**
 * This exception should wrap {@link org.springframework.dao.DataIntegrityViolationException }
 * in order to provide more descriptive error messagew
 */
public class UniqueConstraintViolation extends RuntimeException {
    public UniqueConstraintViolation() {
    }

    public UniqueConstraintViolation(String message) {
        super(message);
    }

    public UniqueConstraintViolation(String message, Throwable cause) {
        super(message, cause);
    }
}
