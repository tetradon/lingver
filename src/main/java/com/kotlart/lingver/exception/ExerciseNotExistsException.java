package com.kotlart.lingver.exception;

public class ExerciseNotExistsException extends RuntimeException {
    public ExerciseNotExistsException() {
    }

    public ExerciseNotExistsException(String message) {
        super(message);
    }

    public ExerciseNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
