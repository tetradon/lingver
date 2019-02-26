package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.rest.dto.TranslationDto;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.ProfileTranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("profile/translations")
public class ProfileTranslationController {
    private final ModelMapper modelMapper;

    private final ProfileTranslationService profileTranslationService;

    @Autowired
    public ProfileTranslationController(ModelMapper modelMapper, ProfileTranslationService profileTranslationService) {
        this.modelMapper = modelMapper;
        this.profileTranslationService = profileTranslationService;
    }

    @PostMapping
    ResponseEntity saveTransaltion(@Valid @RequestBody TranslationDto translationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.badRequest(result.getFieldErrors());
        }
        Translation translation = modelMapper.map(translationDto, Translation.class);
        profileTranslationService.saveTranslationToCurrentProfileDictionary(translation);
        return ResponseEntity.ok().body(modelMapper.map(translation, TranslationDto.class));
    }
}
