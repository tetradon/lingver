package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProfileTranslationService {
    ProfileTranslation addTranslationToActiveProfile(Long translationId);

    Page<ProfileTranslation> getTranslationsOfActiveProfile(Pageable pageable);

    int removeTranslationsFromActiveProfile(List<Long> id);
}
