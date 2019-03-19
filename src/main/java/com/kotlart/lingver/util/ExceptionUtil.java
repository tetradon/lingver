package com.kotlart.lingver.util;

import com.kotlart.lingver.exception.UniqueConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;


public class ExceptionUtil {
    public static void handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        if ((exception.getCause() != null) && (exception.getCause() instanceof ConstraintViolationException)) {
            /*  Postgres driver doesn't give access to the violated constraint name through calling {@link org.hibernate.exception.ConstraintViolationException#getConstraintName()}
                So the only way to get the field name is from string returned by {@link java.sql.SQLException#getMessage()} which looks like this
                "ERROR: duplicate key value violates unique constraint "profile_username_uindex" Detail: Key (username)=(asd) already exists."
            */
            final String fullErrorMessage = ((ConstraintViolationException) exception.getCause()).getSQLException().getMessage();
            final String message = fullErrorMessage.substring(fullErrorMessage.indexOf("Key"));
            throw new UniqueConstraintViolation(message, exception);
        } else {
            throw exception;
        }
    }
}
