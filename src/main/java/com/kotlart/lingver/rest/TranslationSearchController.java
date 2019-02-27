package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.rest.dto.ValueDto;
import com.kotlart.lingver.service.TranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/translations")
public class TranslationSearchController {
    private final TranslationService translationService;
    private final ModelMapper modelMapper;

    @Autowired
    public TranslationSearchController(TranslationService translationService, ModelMapper modelMapper) {
        this.translationService = translationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    ResponseEntity getTranslations(@RequestParam("word") String wordToSearch) {
        final List<Translation> foundTranslations = translationService.findByWordValue(wordToSearch);
        final List<ValueDto> convertedDtos = foundTranslations
                .stream()
                .map(word -> modelMapper.map(word, ValueDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(convertedDtos);
    }
}
