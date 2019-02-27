package com.kotlart.lingver.rest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileTranslationDto {
    private Long id;
    private TranslationDto translation;
    private Date insertDate;
    private String example;
    private String description;
}
