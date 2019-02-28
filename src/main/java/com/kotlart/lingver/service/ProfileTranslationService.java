package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileTranslationService {
    ProfileTranslation saveTranslationToCurrentProfile(Long translationId);

    Page<ProfileTranslation> getTranslationsOfCurrentProfile(Pageable pageable);
}
