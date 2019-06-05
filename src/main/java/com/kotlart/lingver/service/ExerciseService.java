package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.model.strategy.ExerciseStrategy;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItemDto> prepareExercise(List<Long> translationIds, ExerciseStrategy exerciseStrategy);
    void saveResult(ExerciseResultDto result);
}
