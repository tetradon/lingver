package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.model.ProfileTranslation;
import com.kotlart.lingver.model.QueryParameter;
import com.kotlart.lingver.model.Role;
import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.model.Word;
import com.kotlart.lingver.service.impl.ProfileTranslationServiceBean;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileTranslationServiceBeanTest {
    private final String WORD_VALUE = "test";
    private final String TRANSLATION_VALUE = "тест";
    private final QueryParameter QUERY_PARAMETERS = new QueryParameter();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProfileTranslationRepository profileTranslationRepository;

    @Autowired
    private TranslationRepository translationRepository;

    private ProfileTranslationServiceBean sut;

    private Profile profile;

    @Before
    public void before() {

        final Role role = entityManager.persist(Role.builder().authority(Role.USER).build());
        profile = entityManager.persist(Profile.builder().username("test").email("test").password("test").authorities(Collections.singletonList(role)).build());

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(profile);
        SecurityContextHolder.setContext(securityContext);

        sut = new ProfileTranslationServiceBean(profileTranslationRepository, translationRepository);
    }

    @Test
    public void test_getTranslationsOfActiveProfile_unpaged() {

        Assert.assertEquals(0, sut.getTranslationsOfActiveProfile(QUERY_PARAMETERS).getTotalElements());

        Word word = entityManager.persist(Word.builder().value(WORD_VALUE).build());
        Translation translation = entityManager.persist(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        entityManager.persist(ProfileTranslation.builder().profile(profile).translation(translation).build());
        final Page<ProfileTranslation> translationsOfActiveProfile = sut.getTranslationsOfActiveProfile(QUERY_PARAMETERS);
        Assert.assertEquals(1, translationsOfActiveProfile.getTotalElements());
        Assert.assertEquals(translation, translationsOfActiveProfile.getContent().get(0).getTranslation());
    }

    @Test
    public void test_removeTranslationsFromActiveProfile() {
        Word word = entityManager.persist(Word.builder().value(WORD_VALUE).build());
        Translation translation = entityManager.persist(Translation.builder().value(TRANSLATION_VALUE).word(word).build());

        final ProfileTranslation profileTranslation = entityManager.persist(ProfileTranslation.builder().profile(profile).translation(translation).build());
        Assert.assertEquals(1, sut.getTranslationsOfActiveProfile(QUERY_PARAMETERS).getTotalElements());
        Assert.assertEquals(1, sut.removeTranslationsFromActiveProfile(Collections.singletonList(profileTranslation.getId())));
        Assert.assertEquals(0, sut.getTranslationsOfActiveProfile(QUERY_PARAMETERS).getTotalElements());
    }

    @Test
    public void test_addTranslationToActiveProfile() {
        Word word = entityManager.persist(Word.builder().value(WORD_VALUE).build());
        Translation translation = entityManager.persist(Translation.builder().value(TRANSLATION_VALUE).word(word).build());
        final ProfileTranslation profileTranslation = sut.addTranslationToActiveProfile(translation.getId());
        Assert.assertNotNull(profileTranslation);
        Assert.assertNotNull(profileTranslation.getProfile());
        Assert.assertNotNull(profileTranslation.getTranslation());
    }
}
