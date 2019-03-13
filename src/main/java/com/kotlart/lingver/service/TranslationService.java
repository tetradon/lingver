package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.service.projection.TranslationRatingProjection;

import java.util.List;

public interface TranslationService {
    List<TranslationRatingProjection> findByWordValue(String word);

    Translation createTranslationForWord(String translation, String word);
}
