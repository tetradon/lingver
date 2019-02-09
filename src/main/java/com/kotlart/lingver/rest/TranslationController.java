package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/translation")
public class TranslationController {
    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping
    List<Translation> getTranslations(@RequestParam("word") String word) {
        return translationService.findByWordValue(word);
    }
}
