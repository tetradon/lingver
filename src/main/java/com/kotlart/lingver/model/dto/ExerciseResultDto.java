package com.kotlart.lingver.model.dto;

import lombok.Data;

@Data
public class ExerciseResultDto {
    private Long profileTranslationId;
    private boolean answerCorrect;
    private Long exerciseId;
}
