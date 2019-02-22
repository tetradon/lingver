package com.kotlart.lingver.rest;

import com.kotlart.lingver.rest.dto.MessageDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity databaseError(DataIntegrityViolationException ex) throws DataIntegrityViolationException {

        List<MessageDto> responseMessages = new ArrayList<>();
        if ((ex.getCause() != null) && (ex.getCause() instanceof ConstraintViolationException)) {
            //  fullErrorMessage for postgresql is look like this -
            //  ERROR: duplicate key value violates unique constraint "profile_username_uindex" Detail: Key (username)=(asd) already exists.
            final String fullErrorMessage = ((ConstraintViolationException) ex.getCause()).getSQLException().getMessage();
            final String nameOfField = fullErrorMessage.substring(
                    fullErrorMessage.indexOf("(") + 1, fullErrorMessage.indexOf(")"));
            final MessageDto messageDto = new MessageDto(nameOfField + " already exists");
            responseMessages.add(messageDto);
        }
        return ResponseEntity.badRequest().body(responseMessages);
    }
}
