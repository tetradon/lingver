package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.respository.TranslationRepository;
import com.kotlart.lingver.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationServiceBean implements TranslationService {

    private final TranslationRepository translationRepository;

    @Autowired
    public TranslationServiceBean(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public List<Translation> findByWordValue(String word) {
        return translationRepository.findByWordValue(word);
    }
}
