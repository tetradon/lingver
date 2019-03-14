package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.rest.dto.AggregatedProfileTranslationsDto;
import com.kotlart.lingver.rest.dto.IdListDto;
import com.kotlart.lingver.rest.dto.PageDto;
import com.kotlart.lingver.rest.dto.ProfileTranslationDto;
import com.kotlart.lingver.rest.dto.ValueDto;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.ProfileTranslationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity getProfileTranslations(PageDto pageDto) {
        Pageable pageable = PageRequest.of(
                pageDto.getPage(),
                pageDto.getSize(),
                Sort.by(pageDto.getSortDirection(), pageDto.getSortField()));
        final List<ProfileTranslation> paginatedTranslations = profileTranslationService
                .getTranslationsOfActiveProfile(pageable).getContent();

        final List<ProfileTranslationDto> paginatedTranslationDtos = paginatedTranslations
                .stream()
                .map(tr -> modelMapper.map(tr, ProfileTranslationDto.class))
                .collect(Collectors.toList());

        final List<Long> allAddedTranslationIds = profileTranslationService.findAllTranslationIdsOfActiveProfile();
        final AggregatedProfileTranslationsDto responseDto =
                AggregatedProfileTranslationsDto.builder()
                        .translations(paginatedTranslationDtos)
                        .allTranslationIds(allAddedTranslationIds)
                        .build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    ResponseEntity addTransaltionToProfile(@Valid @RequestBody ValueDto translationDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.badRequest(result.getFieldErrors());
        }
        final ProfileTranslation profileTranslation = profileTranslationService.addTranslationToActiveProfile(translationDto.getId());
        return ResponseEntity.ok().body(modelMapper.map(profileTranslation, ProfileTranslationDto.class));
    }

    @DeleteMapping
    ResponseEntity removeTranslationsFromProfile(@RequestBody IdListDto dto) {
        final int removed = profileTranslationService.removeTranslationsFromActiveProfile(dto.getIds());
        return ResponseEntity.ok().body(removed);
    }
}
