package com.kotlart.lingver.model.strategy;

import com.kotlart.lingver.model.entity.ProfileTranslation;

public enum ExerciseStrategy {

    WORD_TRANSLATION {
        @Override
        public String getQuestion(ProfileTranslation profileTranslation) {
            return profileTranslation.getTranslation().getWord().getValue();
        }

        @Override
        public String getAnswer(ProfileTranslation profileTranslation) {
            return profileTranslation.getTranslation().getValue();
        }

        @Override
        public String getExerciseDbName() {
            return this.name();
        }
    },

    TRANSLATION_WORD {
        @Override
        public String getQuestion(ProfileTranslation profileTranslation) {
            return profileTranslation.getTranslation().getValue();
        }

        @Override
        public String getAnswer(ProfileTranslation profileTranslation) {
            return profileTranslation.getTranslation().getWord().getValue();
        }

        @Override
        public String getExerciseDbName() {
            return this.name();
        }
    };

    abstract public String getQuestion(ProfileTranslation profileTranslation);

    abstract public String getAnswer(ProfileTranslation profileTranslation);

    abstract public String getExerciseDbName();
}
