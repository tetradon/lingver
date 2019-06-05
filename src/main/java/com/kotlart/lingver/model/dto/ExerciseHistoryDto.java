package com.kotlart.lingver.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kotlart.lingver.model.entity.Exercise;
import lombok.Data;

import java.util.Date;

@Data
public class ExerciseHistoryDto {
    private Long id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date date;
    private Boolean result;
    private ProfileTranslationDto profileTranslation;
    private Exercise exercise;
}
