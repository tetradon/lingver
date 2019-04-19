package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.dto.IdListDto;
import com.kotlart.lingver.model.dto.PaginatedProfileTranslationsDto;
import com.kotlart.lingver.model.dto.ProfileTranslationDto;
import com.kotlart.lingver.model.dto.ValueDto;
import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.ProfileTranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    ResponseEntity getProfileTranslations(QueryParameters queryParameters) {
        Profile activeProfile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Page<ProfileTranslation> translationsPage = profileTranslationService
                .getTranslationsOfProfile(queryParameters, activeProfile);

        final List<ProfileTranslation> paginatedTranslations = translationsPage.getContent();
        final long totalFound = translationsPage.getTotalElements();

        final List<ProfileTranslationDto> profileTranslationDtos = paginatedTranslations.stream().map(pt -> modelMapper.map(pt, ProfileTranslationDto.class)).collect(Collectors.toList());
        final PaginatedProfileTranslationsDto responseDto =
                PaginatedProfileTranslationsDto.builder()
                        .translations(profileTranslationDtos)
                        .total(totalFound)
                        .build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    ResponseEntity addTranslationToProfile(@Valid @RequestBody ValueDto translationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.badRequest(result.getFieldErrors());
        }
        Profile activeProfile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ProfileTranslation profileTranslation = profileTranslationService.addTranslationToProfile(translationDto.getId(), activeProfile);
        return ResponseEntity.ok().body(modelMapper.map(profileTranslation, ProfileTranslationDto.class));
    }

    @DeleteMapping
    ResponseEntity removeTranslationsFromProfile(@RequestBody IdListDto profileTranslationIds) {
        final int removed = profileTranslationService.removeProfileTranslations(profileTranslationIds.getIds());
        return ResponseEntity.ok().body(removed);
    }
}
