package com.kotlart.lingver.service;

import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.model.QueryParameter;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProfileTranslationService {
    ProfileTranslation addTranslationToActiveProfile(Long translationId);

    Page<ProfileTranslation> getTranslationsOfActiveProfile(QueryParameter queryParameter);

    int removeTranslationsFromActiveProfile(List<Long> id);

    List<Long> findAllTranslationIdsOfActiveProfile();
}
