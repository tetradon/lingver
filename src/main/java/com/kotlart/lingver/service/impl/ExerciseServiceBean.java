package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.exception.NotUniqueExcerciseQuestionExeption;
import com.kotlart.lingver.model.dto.AnswerDto;
import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.model.entity.Exercise;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.service.ExerciseService;
import com.kotlart.lingver.service.respository.ExerciseHistoryRepository;
import com.kotlart.lingver.service.respository.ExerciseRepository;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExerciseServiceBean implements ExerciseService {

    private final ProfileTranslationRepository profileTranslationRepository;
    private final ExerciseHistoryRepository exerciseHistoryRepository;
    private final ExerciseRepository exerciseRepository;


    @Autowired
    public ExerciseServiceBean(ProfileTranslationRepository profileTranslationRepository,
                               ExerciseHistoryRepository exerciseHistoryRepository,
                               ExerciseRepository exerciseRepository) {
        this.profileTranslationRepository = profileTranslationRepository;
        this.exerciseHistoryRepository = exerciseHistoryRepository;
        this.exerciseRepository = exerciseRepository;
    }


    @Override
    public List<ExerciseItemDto> generateWordTranslationTrainingSet(List<Long> profileTranslationIds) {
        final List<ExerciseItemDto> result = new ArrayList<>();
        final List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(profileTranslationIds);

        final List<String> words = getWordValuesStream(profileTranslations).collect(Collectors.toList());
        if (CollectionUtil.hasDuplicate(words)) {
            throw new NotUniqueExcerciseQuestionExeption("Only unique words can be present in one exercise");
        }

        Exercise exercise = exerciseRepository.findByName(Exercise.Name.WORD_TRANSLATION);
        final Set<String> translations = getTranslationValuesStream(profileTranslations).collect(Collectors.toSet());

        profileTranslations.forEach(profileTranslation -> {
                    ExerciseItemDto exerciseItem = ExerciseItemDto.builder()
                            .profileTranslationId(profileTranslation.getId())
                            .question(profileTranslation.getTranslation().getWord().getValue())
                            .answers(generateResponseVariants(translations, profileTranslation.getTranslation().getValue()))
                            .exerciseId(exercise.getId())
                            .build();
                    result.add(exerciseItem);
                }
        );
        return result;
    }


    @Override
    public List<ExerciseItemDto> generateTranslationWordTrainingSet(List<Long> profileTranslationIds) {
        final List<ExerciseItemDto> result = new ArrayList<>();
        final List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(profileTranslationIds);

        final List<String> translations = getTranslationValuesStream(profileTranslations).collect(Collectors.toList());
        if (CollectionUtil.hasDuplicate(translations)) {
            throw new NotUniqueExcerciseQuestionExeption("Only unique translations can be present in one exercise");
        }

        Exercise exercise = exerciseRepository.findByName(Exercise.Name.TRANSLATION_WORD);
        final Set<String> words = getWordValuesStream(profileTranslations).collect(Collectors.toSet());

        profileTranslations.forEach(profileTranslation -> {
            ExerciseItemDto exerciseItem = ExerciseItemDto.builder()
                    .profileTranslationId(profileTranslation.getId())
                    .question(profileTranslation.getTranslation().getValue())
                    .answers(generateResponseVariants(words, profileTranslation.getTranslation().getWord().getValue()))
                    .exerciseId(exercise.getId())
                    .build();
                    result.add(exerciseItem);
                }
        );
        return result;
    }

    @Override
    public void saveResults(List<ExerciseResultDto> results) {
        for (ExerciseResultDto dto : results) {
            exerciseHistoryRepository.saveExerciseResult(dto.getProfileTranslationId(),
                    dto.getExerciseId(), dto.getAnswerCorrect());
        }
    }

    private Stream<String> getWordValuesStream(List<ProfileTranslation> profileTranslations) {
        return profileTranslations
                .stream()
                .map(profileTranslation -> profileTranslation.getTranslation().getWord().getValue());
    }

    private Stream<String> getTranslationValuesStream(List<ProfileTranslation> profileTranslations) {
        return profileTranslations
                .stream()
                .map(profileTranslation -> profileTranslation.getTranslation().getValue());
    }

    private List<AnswerDto> generateResponseVariants(Set<String> allTranslationValues, String correct) {
        List<String> incorrectAnswerList = CollectionUtil.createListFromCollectionWithoutElement(allTranslationValues, correct);
        int maxNumberOfIncorrectAnswers = 4;

        if (incorrectAnswerList.size() >= maxNumberOfIncorrectAnswers) {
            int numberOfElementsToDelete = incorrectAnswerList.size() - maxNumberOfIncorrectAnswers;
            CollectionUtil.removeRandomlyFromList(incorrectAnswerList, numberOfElementsToDelete);
        }

        List<AnswerDto> answers = createResponseVariantList(correct, incorrectAnswerList);
        Collections.shuffle(answers);

        return answers;
    }


    private List<AnswerDto> createResponseVariantList(String correctAnswer, List<String> incorrectAnswers) {
        List<AnswerDto> result = new ArrayList<>();
        result.add(AnswerDto.builder()
                .value(correctAnswer)
                .isCorrect(true)
                .build());
        incorrectAnswers.forEach(v ->
                result.add(AnswerDto.builder()
                        .value(v)
                        .isCorrect(false)
                        .build())
        );
        return result;
    }


}
