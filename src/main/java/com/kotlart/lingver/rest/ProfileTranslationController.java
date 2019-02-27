package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.rest.dto.ProfileTranslationDto;
import com.kotlart.lingver.rest.dto.ValueDto;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.ProfileTranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    ResponseEntity getProfileTransaltions() {
        final List<ProfileTranslation> profileTranslations = profileTranslationService.getTranslationsOfCurrentProfile();
        final List<ProfileTranslationDto> translationDtos = profileTranslations.stream().map(tr -> modelMapper.map(tr, ProfileTranslationDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(translationDtos);
    }

    @PostMapping
    ResponseEntity addTransaltionToProfile(@Valid @RequestBody ValueDto translationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.badRequest(result.getFieldErrors());
        }
        profileTranslationService.addTranslationToCurrentProfile(translationDto.getId());
        return ResponseEntity.ok().body(modelMapper.map(translationDto, ValueDto.class));
    }


}
