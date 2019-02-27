package com.kotlart.lingver.rest.dto;

import lombok.Data;

@Data
public class TranslationDto {
    private Long id;
    private String value;
    private ValueDto word;
}
