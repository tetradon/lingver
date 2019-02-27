package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ProfileTranslation;

import java.util.List;

public interface ProfileTranslationService {
    ProfileTranslation addTranslationToCurrentProfile(Long translationId);

    List<ProfileTranslation> getTranslationsOfCurrentProfile();
}
