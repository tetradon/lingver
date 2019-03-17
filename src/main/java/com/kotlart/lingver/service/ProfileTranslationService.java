package com.kotlart.lingver.service;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProfileTranslationService {
    ProfileTranslation addTranslationToActiveProfile(Long translationId);

    Page<ProfileTranslation> getTranslationsOfActiveProfile(QueryParameters queryParameters);

    int removeTranslationsFromActiveProfile(List<Long> id);

    List<Long> findAllTranslationIdsOfActiveProfile();
}
