package com.kotlart.lingver.service;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.service.impl.ProfileTranslationServiceBean;
import com.kotlart.lingver.service.respository.ProfileRepository;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import com.kotlart.lingver.service.respository.WordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileTranslationServiceBeanTest {
    private final String WORD_VALUE = "test";
    private final String TRANSLATION_VALUE = "тест";
    private final QueryParameters QUERY_PARAMETERS = new QueryParameters();

    @Autowired
    private ProfileTranslationRepository profileTranslationRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private ProfileTranslationServiceBean sut;

    private Profile profile;

    @Before
    public void before() {
        final Role role = Role.builder().authority(Role.USER).build();
        profile = profileRepository.save(Profile.builder().username("test").email("test").password("test").authorities(Collections.singletonList(role)).build());
        sut = new ProfileTranslationServiceBean(profileTranslationRepository, translationRepository);
    }

    @Test
    public void test_getTranslationsOfActiveProfile_unpaged() {
        Assert.assertEquals(0, sut.getTranslationsOfProfile(QUERY_PARAMETERS, profile).getTotalElements());

        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        profileTranslationRepository.save(ProfileTranslation.builder().profile(profile).translation(translation).build());

        final Page<ProfileTranslation> translationsOfActiveProfile = sut.getTranslationsOfProfile(QUERY_PARAMETERS, profile);
        Assert.assertEquals(1, translationsOfActiveProfile.getTotalElements());
        Assert.assertEquals(translation, translationsOfActiveProfile.getContent().get(0).getTranslation());
    }

    @Test
    public void test_removeTranslationsFromActiveProfile() {
        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());

        final ProfileTranslation profileTranslation = profileTranslationRepository.save(ProfileTranslation.builder().profile(profile).translation(translation).build());
        Assert.assertEquals(1, sut.getTranslationsOfProfile(QUERY_PARAMETERS, profile).getTotalElements());
        Assert.assertEquals(1, sut.removeTranslationsFromProfile(Collections.singletonList(profileTranslation.getId()), profile));
        Assert.assertEquals(0, sut.getTranslationsOfProfile(QUERY_PARAMETERS, profile).getTotalElements());
    }

    @Test
    public void test_addTranslationToActiveProfile() {
        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        final ProfileTranslation profileTranslation = sut.addTranslationToProfile(translation.getId(), profile);
        Assert.assertNotNull(profileTranslation);
        Assert.assertNotNull(profileTranslation.getProfile());
        Assert.assertNotNull(profileTranslation.getTranslation());
    }
}
