package com.kotlart.lingver.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TranslationDto {
    @NotNull
    private Long id;
    @NotBlank
    private String value;
}
