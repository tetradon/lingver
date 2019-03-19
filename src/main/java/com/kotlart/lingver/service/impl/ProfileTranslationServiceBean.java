package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.service.ProfileTranslationService;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import com.kotlart.lingver.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ProfileTranslation addTranslationToProfile(Long translationId, Profile profile) {
        final Translation translationEntity = translationRepository.findById(translationId).orElse(null);
        ProfileTranslation profileTranslation = new ProfileTranslation();
        profileTranslation.setProfile(profile);
        profileTranslation.setTranslation(translationEntity);
        try {
            return profileTranslationRepository.save(profileTranslation);
        } catch (DataIntegrityViolationException exception) {
            ExceptionUtil.handleDataIntegrityViolationException(exception);
        }
        return null;
    }

    @Override
    public Page<ProfileTranslation> getTranslationsOfProfile(QueryParameters queryParameters, Profile profile) {
        Pageable pageable = PageRequest.of(
                queryParameters.getPage(),
                queryParameters.getSize(),
                Sort.by(queryParameters.getSortDirection(), queryParameters.getSortField()));
        return profileTranslationRepository
                .findAllByProfileIdAndByWordValueOrTranslationValue(
                        profile.getId(),
                        queryParameters.getSearch(),
                        pageable);
    }

    @Override
    public int removeTranslationsFromProfile(List<Long> ids, Profile profile) {
        return profileTranslationRepository.deleteAllByIds(ids);
    }

    @Override
    public List<Long> findAllTranslationIdsOfProfile(Profile profile) {
        Profile activeUser = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileTranslationRepository.findAllTranslationIdsByProfileId(activeUser.getId());
    }
}
