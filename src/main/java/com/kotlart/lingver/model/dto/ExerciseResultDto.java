package com.kotlart.lingver.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseResultDto {
    private List<Long> profileTranslationIds;
    private boolean answerCorrect;
    private Long exerciseId;
}
