package com.kotlart.lingver.service;

import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.projection.TranslationSearchProjection;

import java.util.List;

public interface TranslationService {
    /**
     * Returns list of translations for corresponding word, with translation rating
     * and flag that shows if this translation added for requested profile
     *
     * @param word      word value to search
     * @param profileId id of the profile that should be checked on presence of translation
     * @return TranslationSearchProjection
     */
    List<TranslationSearchProjection> findByWordValue(String word, Long profileId);

    Translation createTranslationForWord(String translation, String word);
}
