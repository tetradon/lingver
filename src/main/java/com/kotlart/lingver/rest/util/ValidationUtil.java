package com.kotlart.lingver.rest.util;

import com.kotlart.lingver.rest.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {
    public static ResponseEntity badRequest(List<FieldError> errors) {
        List<MessageDto> violations = new ArrayList<>();

        for (FieldError error : errors) {
            final MessageDto dto = new MessageDto(error.getField() + " " + error.getDefaultMessage());
            violations.add(dto);
        }
        return ResponseEntity.badRequest().body(violations);
    }
}