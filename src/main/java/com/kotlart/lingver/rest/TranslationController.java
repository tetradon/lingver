package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.rest.dto.NewTranslationDto;
import com.kotlart.lingver.rest.dto.TranslationDto;
import com.kotlart.lingver.rest.dto.ValueDto;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.TranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/translations")
public class TranslationController {
    private final TranslationService translationService;
    private final ModelMapper modelMapper;

    @Autowired
    public TranslationController(TranslationService translationService, ModelMapper modelMapper) {
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

    @PostMapping
    ResponseEntity addTranslation(@Valid @RequestBody NewTranslationDto newTranslationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.badRequest(bindingResult.getFieldErrors());
        }
        Translation translation = translationService.createTranslationForWord(newTranslationDto.getTranslation(), newTranslationDto.getWord());
        return ResponseEntity.ok().body(modelMapper.map(translation, TranslationDto.class));
    }
}
