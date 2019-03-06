package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Translation;

import java.util.List;

public interface TranslationService {
    List<Translation> findByWordValue(String word);

    Translation createTranslationForWord(String translation, String word);

    int countRatingForTranslation(Long translation);
}
