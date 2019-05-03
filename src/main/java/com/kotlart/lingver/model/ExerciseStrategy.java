package com.kotlart.lingver.model;

import com.kotlart.lingver.model.entity.ProfileTranslation;

public interface ExerciseStrategy {
    String getQuestion(ProfileTranslation profileTranslation);
    String getAnswer(ProfileTranslation profileTranslation);
}
