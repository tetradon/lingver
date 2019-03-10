package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.service.dto.RatingTranslationDto;

import java.util.List;

public interface TranslationService {
    List<RatingTranslationDto> findByWordValue(String word);

    Translation createTranslationForWord(String translation, String word);
}
