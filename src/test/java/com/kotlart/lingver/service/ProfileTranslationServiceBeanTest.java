package com.kotlart.lingver.service;

import com.kotlart.lingver.model.QueryParameters;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.model.projection.ProfileTranslationProjection;
import com.kotlart.lingver.service.impl.ProfileTranslationServiceBean;
import com.kotlart.lingver.service.respository.ExerciseHistoryRepository;
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
public class ProfileTranslationServiceBeanTest extends AbstractServiceBeanTest {
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
    private ExerciseHistoryRepository exerciseHistoryRepository;

    private ProfileTranslationServiceBean sut;

    @Before
    public void before() {
        sut = new ProfileTranslationServiceBean(profileTranslationRepository, translationRepository, exerciseHistoryRepository);
    }

    @Test
    public void test_getTranslationsOfActiveProfile_unpaged() {
        Assert.assertEquals(0, sut.getTranslationsOfProfile(QUERY_PARAMETERS, DEFAULT_PROFILE).getTotalElements());

        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        profileTranslationRepository.save(ProfileTranslation.builder().profile(DEFAULT_PROFILE).translation(translation).build());

        final Page<ProfileTranslationProjection> translationsOfActiveProfile = sut.getTranslationsOfProfile(QUERY_PARAMETERS, DEFAULT_PROFILE);
        Assert.assertEquals(1, translationsOfActiveProfile.getTotalElements());
        Assert.assertEquals(TRANSLATION_VALUE, translationsOfActiveProfile.getContent().get(0).getTranslation());
    }

    @Test
    public void test_removeTranslationsFromActiveProfile() {
        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());

        final ProfileTranslation profileTranslation = profileTranslationRepository.save(ProfileTranslation.builder().profile(DEFAULT_PROFILE).translation(translation).build());
        Assert.assertEquals(1, sut.getTranslationsOfProfile(QUERY_PARAMETERS, DEFAULT_PROFILE).getTotalElements());
        Assert.assertEquals(1, sut.removeProfileTranslations(Collections.singletonList(profileTranslation.getId())));
        Assert.assertEquals(0, sut.getTranslationsOfProfile(QUERY_PARAMETERS, DEFAULT_PROFILE).getTotalElements());
    }

    @Test
    public void test_addTranslationToActiveProfile() {
        Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        Translation translation = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        final ProfileTranslation profileTranslation = sut.addTranslationToProfile(translation.getId(), DEFAULT_PROFILE);
        Assert.assertNotNull(profileTranslation);
        Assert.assertNotNull(profileTranslation.getProfile());
        Assert.assertNotNull(profileTranslation.getTranslation());
    }
}
