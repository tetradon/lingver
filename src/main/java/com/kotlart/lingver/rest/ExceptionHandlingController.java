package com.kotlart.lingver.rest;

import com.kotlart.lingver.rest.dto.MessageDto;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity uniqueConstraintException(DataIntegrityViolationException ex) throws DataIntegrityViolationException {
        MessageDto response = null;
        if ((ex.getCause() != null) && (ex.getCause() instanceof ConstraintViolationException)) {
            /*  Postgres driver doesn't give access to the violated constraint name through calling {@link org.hibernate.exception.ConstraintViolationException#getConstraintName()}
                So the only way to get the field name is from string returned by {@link java.sql.SQLException#getMessage()} which looks like this
                "ERROR: duplicate key value violates unique constraint "profile_username_uindex" Detail: Key (username)=(asd) already exists."
            */
            final String fullErrorMessage = ((ConstraintViolationException) ex.getCause()).getSQLException().getMessage();
            final String nameOfField = fullErrorMessage.substring(
                    fullErrorMessage.indexOf("(") + 1, fullErrorMessage.indexOf(")"));
            response = new MessageDto(nameOfField + " already exists");

        }
        return ResponseEntity.badRequest().body(Collections.singletonList(response));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity serverError(Exception ex) throws DataIntegrityViolationException {
        LOG.error("UNCAUGHT EXCEPTION: ", ex);
        final MessageDto response = new MessageDto("something went wrong");
        return ResponseEntity.status(500).body(Collections.singletonList(response));
    }


}
