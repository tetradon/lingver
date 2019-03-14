package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.service.ProfileTranslationService;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileTranslationServiceBean implements ProfileTranslationService {

    private final ProfileTranslationRepository profileTranslationRepository;
    private final TranslationRepository translationRepository;

    @Autowired
    public ProfileTranslationServiceBean(ProfileTranslationRepository profileTranslationRepository,
                                         TranslationRepository translationRepository) {
        this.profileTranslationRepository = profileTranslationRepository;
        this.translationRepository = translationRepository;
    }

    @Override
    public ProfileTranslation addTranslationToActiveProfile(Long translationId) {
        final Translation translationEntity = translationRepository.findById(translationId).orElse(null);
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileTranslation profileTranslation = new ProfileTranslation();
        profileTranslation.setProfile(activeUser);
        profileTranslation.setTranslation(translationEntity);
        return profileTranslationRepository.save(profileTranslation);
    }

    @Override
    public Page<ProfileTranslation> getTranslationsOfActiveProfile(Pageable pageable) {
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileTranslationRepository.findAllByProfileId(activeUser.getId(), pageable);
    }

    @Override
    public int removeTranslationsFromActiveProfile(List<Long> ids) {
        return profileTranslationRepository.deleteAllByIds(ids);
    }

    @Override
    public List<Long> findAllTranslationIdsOfActiveProfile() {
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileTranslationRepository.findAllTranslationIdsByProfileId(activeUser.getId());
    }
}
