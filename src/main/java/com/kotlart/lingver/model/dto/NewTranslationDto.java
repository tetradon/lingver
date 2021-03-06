package com.kotlart.lingver.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewTranslationDto {
    @NotBlank
    private String word;
    @NotBlank
    private String translation;
}
