package com.kotlart.lingver.service;

import com.kotlart.lingver.model.dto.AnswerDto;
import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.entity.Exercise;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.Translation;
import com.kotlart.lingver.model.entity.Word;
import com.kotlart.lingver.service.impl.ExerciseServiceBean;
import com.kotlart.lingver.service.respository.ExerciseHistoryRepository;
import com.kotlart.lingver.service.respository.ExerciseRepository;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.service.respository.TranslationRepository;
import com.kotlart.lingver.service.respository.WordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    private final Long WORD_TRANSLATION_EXERCISE_ID = 1L;
    private final Long TRANSLATION_WORD_EXERCISE_ID = 2L;

    @Autowired
    private ProfileTranslationRepository profileTranslationRepository;
    @Autowired
    private ExerciseHistoryRepository exerciseHistoryRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TranslationRepository translationRepository;
    @Autowired
    private WordRepository wordRepository;

    private ExerciseServiceBean sut;

    @Before
    public void before() {
        Mockito.when(exerciseRepository.findByName(Exercise.Name.WORD_TRANSLATION)).thenReturn(Exercise.builder().id(WORD_TRANSLATION_EXERCISE_ID).name(Exercise.Name.WORD_TRANSLATION).build());
        Mockito.when(exerciseRepository.findByName(Exercise.Name.TRANSLATION_WORD)).thenReturn(Exercise.builder().id(TRANSLATION_WORD_EXERCISE_ID).name(Exercise.Name.WORD_TRANSLATION).build());
        sut = new ExerciseServiceBean(profileTranslationRepository, exerciseHistoryRepository, exerciseRepository);
        prepareTranslations();
    }

    @Test
    public void test_generateWordTranslationTrainingSet() {
        final List<Long> ids = profileTranslationRepository.findAll(Pageable.unpaged()).stream().map(ProfileTranslation::getId).collect(Collectors.toList());
        final List<ExerciseItemDto> exerciseItems = sut.generateWordTranslationTrainingSet(ids);
        Assert.assertEquals(2, exerciseItems.size());
        exerciseItems.forEach(e -> {
            Assert.assertEquals(2, e.getAnswers().size());
            Assert.assertEquals(WORD_TRANSLATION_EXERCISE_ID, e.getExerciseId());
            Assert.assertTrue(Arrays.asList(WORD_VALUE_1, WORD_VALUE_2).contains(e.getQuestion()));

            final List<String> answerValues = e.getAnswers().stream().map(AnswerDto::getValue).collect(Collectors.toList());
            Assert.assertTrue(Arrays.asList(TRANSLATION_VALUE_1, TRANSLATION_VALUE_2).containsAll(answerValues));
        });
    }

    @Test
    public void test_generateTranslationWordTrainingSet() {
        final List<Long> ids = profileTranslationRepository.findAll(Pageable.unpaged()).stream().map(ProfileTranslation::getId).collect(Collectors.toList());
        final List<ExerciseItemDto> exerciseItems = sut.generateTranslationWordTrainingSet(ids);
        Assert.assertEquals(2, exerciseItems.size());
        exerciseItems.forEach(e -> {
            Assert.assertEquals(2, e.getAnswers().size());
            Assert.assertEquals(TRANSLATION_WORD_EXERCISE_ID, e.getExerciseId());
            Assert.assertTrue(Arrays.asList(TRANSLATION_VALUE_1, TRANSLATION_VALUE_2).contains(e.getQuestion()));

            final List<String> answerValues = e.getAnswers().stream().map(AnswerDto::getValue).collect(Collectors.toList());
            Assert.assertTrue(Arrays.asList(WORD_VALUE_1, WORD_VALUE_2).containsAll(answerValues));
        });
    }

    private void prepareTranslations() {
        Word word1 = wordRepository.save(Word.builder().value(WORD_VALUE_1).build());
        Translation translation1 = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE_1).word(word1).build());
        profileTranslationRepository.save(ProfileTranslation.builder().profile(DEFAULT_PROFILE).translation(translation1).build());

        Word word2 = wordRepository.save(Word.builder().value(WORD_VALUE_2).build());
        Translation translation2 = translationRepository.save(Translation.builder().value(TRANSLATION_VALUE_2).word(word2).build());
        profileTranslationRepository.save(ProfileTranslation.builder().profile(DEFAULT_PROFILE).translation(translation2).build());
    }
}
