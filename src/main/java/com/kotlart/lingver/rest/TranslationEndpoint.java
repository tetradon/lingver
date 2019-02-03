package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.respository.WordRepository;
import com.kotlart.lingver.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/translation")
public class TranslationEndpoint {
    private final TranslationService translationService;
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    public TranslationEndpoint(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping
    ResponseEntity<?> getTranslations(@RequestParam("word") String word) {
        System.out.println(word);
        List<Translation> translations = translationService.findByWordValue(word);
        return ResponseEntity.ok().body(translations);
    }
}
