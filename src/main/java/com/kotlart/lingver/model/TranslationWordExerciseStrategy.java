package com.kotlart.lingver.model;

import com.kotlart.lingver.model.entity.ProfileTranslation;

public class TranslationWordExerciseStrategy implements ExerciseStrategy {
    @Override
    public String getQuestion(ProfileTranslation profileTranslation) {
        return profileTranslation.getTranslation().getValue();
    }

    @Override
    public String getAnswer(ProfileTranslation profileTranslation) {
        return profileTranslation.getTranslation().getWord().getValue();
    }
}
