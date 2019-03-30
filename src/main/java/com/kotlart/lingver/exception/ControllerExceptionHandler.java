package com.kotlart.lingver.exception;

import com.kotlart.lingver.model.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({UniqueConstraintViolation.class, NotUniqueExcerciseQuestionExeption.class})
    public ResponseEntity uniqueConstraint(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Collections.singletonList(new MessageDto(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity serverError(Exception ex) {
        LOG.error("UNCAUGHT EXCEPTION: ", ex);
        final MessageDto response = new MessageDto("something went wrong");
        return ResponseEntity.status(500).body(Collections.singletonList(response));
    }


}
