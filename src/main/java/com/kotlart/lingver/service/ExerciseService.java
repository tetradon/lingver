package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.model.entity.ExerciseHistory;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItemDto> generateWordTranslationTrainingSet(List<Long> translationIds);

    List<ExerciseItemDto> generateTranslationWordTrainingSet(List<Long> translationIds);

    ExerciseHistory saveResult(ExerciseResultDto result);
}
