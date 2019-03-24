package com.kotlart.lingver.model.dto;

import lombok.Data;

@Data
public class ExerciseResultDto {
    private Long profileTranslationId;
    private Boolean answerCorrect;
    private Long exerciseId;
}
