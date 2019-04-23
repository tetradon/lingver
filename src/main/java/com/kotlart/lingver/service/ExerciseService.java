package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItemDto> generateWordTranslationTrainingSet(List<Long> translationIds);

    List<ExerciseItemDto> generateTranslationWordTrainingSet(List<Long> translationIds);

    void saveResult(ExerciseResultDto result);
}
