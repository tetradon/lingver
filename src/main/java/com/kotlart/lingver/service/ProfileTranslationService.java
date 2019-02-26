package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.model.Translation;

public interface ProfileTranslationService {
    ProfileTranslation saveTranslationToCurrentProfileDictionary(Translation translation);
}
