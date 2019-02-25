package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Word;

public interface UserTranslationService {
    void addWordToCurrentUserDictionary(Word word);
}
