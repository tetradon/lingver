package com.kotlart.lingver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileTranslationDto {
    private Long id;
    @JsonProperty("translation")
    private String translationValue;
    @JsonProperty("word")
    private String translationWordValue;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date insertDate;
    private String example;
    private String description;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date lastRepeatDate;
    private int repeatCount;
    private int successRepeatCount;
}
