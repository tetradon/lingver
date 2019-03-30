package com.kotlart.lingver.exception;

public class NotUniqueExcerciseQuestionExeption extends RuntimeException {
    public NotUniqueExcerciseQuestionExeption() {
    }

    public NotUniqueExcerciseQuestionExeption(String message) {
        super(message);
    }

    public NotUniqueExcerciseQuestionExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
