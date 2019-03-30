package com.kotlart.lingver.service;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.model.projection.TranslationSearchProjection;
import com.kotlart.lingver.service.impl.TranslationServiceBean;
import com.kotlart.lingver.service.respository.ProfileRepository;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.RoleRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import com.kotlart.lingver.service.respository.WordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TranslationServiceBeanTest {

    private final String WORD_VALUE = "test";
    private final String TRANSLATION_VALUE_1 = "тест";
    private final String TRANSLATION_VALUE_2 = "испытание";

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ProfileTranslationRepository profileTranslationRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Profile testProfile;

    private TranslationServiceBean sut;

    @Before
    public void before() {
        sut = new TranslationServiceBean(translationRepository, wordRepository);
        final Role role = roleRepository.save(Role.builder().id(1L).authority(Role.USER).build());
        testProfile = profileRepository.save(Profile.builder().username("test").email("test").password("test").authorities(Collections.singletonList(role)).build());

    }

    @Test
    public void test_findByWordValue() {
        final Word word = wordRepository.save(Word.builder().value(WORD_VALUE).build());
        final Translation translation1 = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE_1).word(word).build());
        final Translation translation2 = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE_2).word(word).build());


        //Saving translation1 to testProfile, so translation1 rating will be equals to 1
        profileTranslationRepository.save(ProfileTranslation
                .builder()
                .profile(testProfile)
                .translation(translation1)
                .build());

        final List<TranslationSearchProjection> foundTranslations = sut.findByWordValue(WORD_VALUE, testProfile.getId());

        Assert.assertEquals(2, foundTranslations.size());

        //Translations ordered by rating, that is why translation1 should be at 0 index in returned list
        //Translation rating is a number of ProfileTranslation rows in which translation equals to searched
        Assert.assertEquals(translation1.getId(), foundTranslations.get(0).getId());
        Assert.assertEquals(TRANSLATION_VALUE_1, foundTranslations.get(0).getValue());
        Assert.assertEquals(1, foundTranslations.get(0).getRating());

        Assert.assertEquals(translation2.getId(), foundTranslations.get(1).getId());
        Assert.assertEquals(TRANSLATION_VALUE_2, foundTranslations.get(1).getValue());
        Assert.assertEquals(0, foundTranslations.get(1).getRating());
    }

    @Test
    public void test_createTranslationForWord() {
        Assert.assertEquals(0, sut.findByWordValue(WORD_VALUE, testProfile.getId()).size());

        final Translation newTranslationForNewWord = sut.createTranslationForWord(TRANSLATION_VALUE_1, WORD_VALUE);
        Assert.assertEquals(TRANSLATION_VALUE_1, newTranslationForNewWord.getValue());
        Assert.assertNotNull(newTranslationForNewWord.getWord());
        Assert.assertEquals(WORD_VALUE, newTranslationForNewWord.getWord().getValue());

        final Translation newTranslationForExistingWord = sut.createTranslationForWord(TRANSLATION_VALUE_2, WORD_VALUE);
        Assert.assertEquals(TRANSLATION_VALUE_2, newTranslationForExistingWord.getValue());
        Assert.assertNotNull(newTranslationForNewWord.getWord());
        Assert.assertEquals(WORD_VALUE, newTranslationForNewWord.getWord().getValue());
    }
}
