package com.kotlart.lingver.service;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProfileTranslationService {
    ProfileTranslation addTranslationToProfile(Long translationId, Profile profile);

    Page<ProfileTranslation> getTranslationsOfProfile(QueryParameters queryParameters, Profile profile);

    int removeTranslationsFromProfile(List<Long> id, Profile profile);
}
