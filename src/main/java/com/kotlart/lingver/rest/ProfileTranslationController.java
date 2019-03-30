package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.dto.IdListDto;
import com.kotlart.lingver.model.dto.PaginatedProfileTranslationsDto;
import com.kotlart.lingver.model.dto.ProfileTranslationDto;
import com.kotlart.lingver.model.dto.ValueDto;
import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.projection.ProfileTranslationProjection;
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
import java.util.ArrayList;
import java.util.List;

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
        final Page<ProfileTranslationProjection> translationsPage = profileTranslationService
                .getTranslationsOfProfile(queryParameters, activeProfile);
        final List<ProfileTranslationProjection> paginatedTranslations = translationsPage.getContent();
        final long totalFound = translationsPage.getTotalElements();

        final List<ProfileTranslationDto> profileTranslationDtos = new ArrayList<>();

        final PaginatedProfileTranslationsDto responseDto =
                PaginatedProfileTranslationsDto.builder()
                        .translations(paginatedTranslations)
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
    ResponseEntity removeTranslationsFromProfile(@RequestBody IdListDto dto) {
        Profile activeProfile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final int removed = profileTranslationService.removeTranslationsFromProfile(dto.getIds(), activeProfile);
        return ResponseEntity.ok().body(removed);
    }
}
