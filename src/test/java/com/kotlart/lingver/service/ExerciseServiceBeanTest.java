package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.AnswerDto;
import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.entity.Exercise;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.model.strategy.ExerciseStrategy;
import com.kotlart.lingver.service.impl.ExerciseServiceBean;
import com.kotlart.lingver.service.respository.ExerciseRepository;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class ExerciseServiceBeanTest extends AbstractServiceBeanTest {

    private final String WORD_VALUE_1 = "test";
    private final String TRANSLATION_VALUE_1 = "тест";
    private final String WORD_VALUE_2 = "code";
    private final String TRANSLATION_VALUE_2 = "код";

    private final Long ID_1 = 1L;
    private final Long ID_2 = 2L;

    @Mock
    private ProfileTranslationRepository profileTranslationRepository;
    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceBean sut;

    private List<Long> profileTranslationIds = new ArrayList<>();

    @Before
    public void before() {
        final List<ProfileTranslation> profileTranslations = prepareProfileTranslations();
        Mockito.when(profileTranslationRepository.findByIdIn(profileTranslationIds)).thenReturn(profileTranslations);
        Mockito.when(exerciseRepository.findByName(ExerciseStrategy.WORD_TRANSLATION.getExerciseDbName())).thenReturn(Exercise.builder().id(ID_1).name(ExerciseStrategy.WORD_TRANSLATION.getExerciseDbName()).build());
        Mockito.when(exerciseRepository.findByName(ExerciseStrategy.TRANSLATION_WORD.getExerciseDbName())).thenReturn(Exercise.builder().id(ID_2).name(ExerciseStrategy.TRANSLATION_WORD.getExerciseDbName()).build());
    }

    @Test
    public void test_generateWordTranslationTrainingSet() {
        final List<ExerciseItemDto> exerciseItems = sut.prepareExercise(profileTranslationIds, ExerciseStrategy.WORD_TRANSLATION);
        Assert.assertEquals(2, exerciseItems.size());
        exerciseItems.forEach(e -> {
            Assert.assertEquals(2, e.getAnswers().size());
            Assert.assertEquals(ID_1, e.getExerciseId());
            Assert.assertTrue(Arrays.asList(WORD_VALUE_1, WORD_VALUE_2).contains(e.getQuestion()));

            final List<String> answerValues = e.getAnswers().stream().map(AnswerDto::getValue).collect(Collectors.toList());
            Assert.assertTrue(Arrays.asList(TRANSLATION_VALUE_1, TRANSLATION_VALUE_2).containsAll(answerValues));
        });
    }

    @Test
    public void test_generateTranslationWordTrainingSet() {
        final List<ExerciseItemDto> exerciseItems = sut.prepareExercise(profileTranslationIds, ExerciseStrategy.TRANSLATION_WORD);
        Assert.assertEquals(2, exerciseItems.size());
        exerciseItems.forEach(e -> {
            Assert.assertEquals(2, e.getAnswers().size());
            Assert.assertEquals(ID_2, e.getExerciseId());
            Assert.assertTrue(Arrays.asList(TRANSLATION_VALUE_1, TRANSLATION_VALUE_2).contains(e.getQuestion()));

            final List<String> answerValues = e.getAnswers().stream().map(AnswerDto::getValue).collect(Collectors.toList());
            Assert.assertTrue(Arrays.asList(WORD_VALUE_1, WORD_VALUE_2).containsAll(answerValues));
        });
    }

    private List<ProfileTranslation> prepareProfileTranslations() {
        List<ProfileTranslation> profileTranslations = new ArrayList<>();

        Word word1 = Word.builder().id(ID_1).value(WORD_VALUE_1).build();
        Translation translation1 = Translation.builder().id(ID_1).value(TRANSLATION_VALUE_1).word(word1).build();
        profileTranslations.add(ProfileTranslation.builder().id(ID_1).translation(translation1).profile(DEFAULT_PROFILE).build());

        Word word2 = Word.builder().id(ID_2).value(WORD_VALUE_2).build();
        Translation translation2 = Translation.builder().id(ID_2).value(TRANSLATION_VALUE_2).word(word2).build();
        profileTranslations.add(ProfileTranslation.builder().id(ID_2).translation(translation2).profile(DEFAULT_PROFILE).build());

        profileTranslationIds.addAll(Arrays.asList(ID_1, ID_2));
        return profileTranslations;
    }
}
