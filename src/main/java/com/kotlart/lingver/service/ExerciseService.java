package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.model.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItemDto> prepareExercise(List<Long> translationIds, Exercise.Name exerciseName);
    void saveResult(ExerciseResultDto result);
}
