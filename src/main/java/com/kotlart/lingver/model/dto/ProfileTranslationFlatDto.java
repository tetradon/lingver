package com.kotlart.lingver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data

public class ProfileTranslationFlatDto {
    private Long id;
    private Long translationId;
    private String translation;
    private Long wordId;
    private String word;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date insertDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date lastRepeatedDate;
    private int numberOfSuccessRepeating;
}
