package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.model.projection.TranslationRatingProjection;
import com.kotlart.lingver.service.TranslationService;
import com.kotlart.lingver.service.respository.TranslationRepository;
import com.kotlart.lingver.service.respository.WordRepository;
import com.kotlart.lingver.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public List<TranslationRatingProjection> findByWordValue(String word) {
        return translationRepository.findByWordValueIgnoreCase(word);
    }

    @Override
    public Translation createTranslationForWord(String translation, String word) {
        Word foundWord = wordRepository.findByValue(word.trim());
        try {
            if (foundWord == null) {
                Word newWord = new Word();
                newWord.setValue(word);
                foundWord = wordRepository.save(newWord);
            }
            Translation newTranslation = new Translation();
            newTranslation.setValue(translation.trim());
            newTranslation.setWord(foundWord);
            return translationRepository.save(newTranslation);

        } catch (DataIntegrityViolationException exception) {
            ExceptionUtil.handleDataIntegrityViolationException(exception);
        }
        return null;
    }
}
