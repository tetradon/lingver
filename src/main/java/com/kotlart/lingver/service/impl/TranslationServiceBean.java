package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.model.Word;
import com.kotlart.lingver.respository.TranslationRepository;
import com.kotlart.lingver.respository.WordRepository;
import com.kotlart.lingver.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationServiceBean implements TranslationService {

    private final TranslationRepository translationRepository;
    private final WordRepository wordRepository;

    @Autowired
    public TranslationServiceBean(TranslationRepository translationRepository, WordRepository wordRepository) {
        this.translationRepository = translationRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public List<Translation> findByWordValue(String word) {
        return translationRepository.findByWordValueIgnoreCase(word);
    }

    @Override
    public Translation createTranslationForWord(String translation, String word) {
        Word foundWord = wordRepository.findByValue(word);
        if (foundWord == null) {
            Word newWord = new Word();
            newWord.setValue(word);
            foundWord = wordRepository.save(newWord);
        }
        Translation newTranslation = new Translation();
        newTranslation.setValue(translation);
        newTranslation.setWord(foundWord);
        return translationRepository.save(newTranslation);
    }
}
