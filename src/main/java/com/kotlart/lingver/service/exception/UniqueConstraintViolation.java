package com.kotlart.lingver.service.exception;

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
