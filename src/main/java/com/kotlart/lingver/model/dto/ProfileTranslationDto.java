package com.kotlart.lingver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileTranslationDto {
    private Long id;
    private TranslationDto translation;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date insertDate;
    private String example;
    private String description;
}
