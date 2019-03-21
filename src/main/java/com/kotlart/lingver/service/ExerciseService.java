package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ExerciseItem;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItem> generateWordTranslationTrainingSet(List<Long> translationIds);
}
