package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.respository.ProfileTranslationRepository;
import com.kotlart.lingver.respository.TranslationRepository;
import com.kotlart.lingver.service.ProfileTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public ProfileTranslation addTranslationToCurrentProfile(Long translationId) {
        final Translation translationEntity = translationRepository.findById(translationId).orElse(null);
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileTranslation profileTranslation = new ProfileTranslation();
        profileTranslation.setProfile(activeUser);
        profileTranslation.setTranslation(translationEntity);
        profileTranslation.setInsertDate(new Date());
        return profileTranslationRepository.save(profileTranslation);
    }

    @Override
    public List<ProfileTranslation> getTranslationsOfCurrentProfile() {
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileTranslationRepository.findAllByProfileId(activeUser.getId());
    }
}
