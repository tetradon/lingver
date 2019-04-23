package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.exception.EntityNotFoundException;
import com.kotlart.lingver.model.dto.AnswerDto;
import com.kotlart.lingver.model.dto.ExerciseItemDto;
import com.kotlart.lingver.model.dto.ExerciseResultDto;
import com.kotlart.lingver.model.entity.Exercise;
import com.kotlart.lingver.model.entity.ExerciseHistory;
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
import java.util.Date;
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
        List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(profileTranslationIds);
        Exercise exercise = exerciseRepository.findByName(Exercise.Name.WORD_TRANSLATION);
        final Set<String> translations = getTranslationValuesStream(profileTranslations).collect(Collectors.toSet());

        for (ProfileTranslation profileTranslation : profileTranslations) {
            if (result.stream().noneMatch(i -> i.getQuestion().equals(profileTranslation.getTranslation().getWord().getValue()))) {
                final Set<ProfileTranslation> profileTranslationsWithSameQuestion = profileTranslations.stream()
                        .filter(pt -> pt.getTranslation().getWord().getValue()
                                .equals(profileTranslation.getTranslation().getWord().getValue()))
                        .collect(Collectors.toSet());

                final Set<Long> ids = profileTranslationsWithSameQuestion.stream().map(ProfileTranslation::getId).collect(Collectors.toSet());
                final Set<String> correctAnswers = profileTranslationsWithSameQuestion.stream().map(pt -> pt.getTranslation().getValue()).collect(Collectors.toSet());
                ExerciseItemDto exerciseItem = ExerciseItemDto.builder()
                        .profileTranslationIds(ids)
                        .question(profileTranslation.getTranslation().getWord().getValue())
                        .answers(generateResponseVariants(translations, correctAnswers))
                        .exerciseId(exercise.getId())
                        .build();
                result.add(exerciseItem);
            }
        }
        return result;
    }


    @Override
    public List<ExerciseItemDto> generateTranslationWordTrainingSet(List<Long> profileTranslationIds) {
        final List<ExerciseItemDto> result = new ArrayList<>();
        final List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(profileTranslationIds);

        Exercise exercise = exerciseRepository.findByName(Exercise.Name.TRANSLATION_WORD);
        final Set<String> words = getWordValuesStream(profileTranslations).collect(Collectors.toSet());

        for (ProfileTranslation profileTranslation : profileTranslations) {
            if (result.stream().noneMatch(i -> i.getQuestion().equals(profileTranslation.getTranslation().getValue()))) {
                final Set<ProfileTranslation> profileTranslationsWithSameQuestion = profileTranslations.stream()
                        .filter(pt -> pt.getTranslation().getValue()
                                .equals(profileTranslation.getTranslation().getValue()))
                        .collect(Collectors.toSet());

                final Set<Long> ids = profileTranslationsWithSameQuestion.stream().map(ProfileTranslation::getId).collect(Collectors.toSet());
                final Set<String> correctAnswers = profileTranslationsWithSameQuestion.stream().map(pt -> pt.getTranslation().getWord().getValue()).collect(Collectors.toSet());
                ExerciseItemDto exerciseItem = ExerciseItemDto.builder()
                        .profileTranslationIds(ids)
                        .question(profileTranslation.getTranslation().getValue())
                        .answers(generateResponseVariants(words, correctAnswers))
                        .exerciseId(exercise.getId())
                        .build();
                result.add(exerciseItem);
            }
        }

        return result;
    }


    @Override
    public void saveResult(ExerciseResultDto result) {
        final List<ProfileTranslation> profileTranslations = profileTranslationRepository
                .findByIdIn(result.getProfileTranslationIds());
        final Exercise exercise = exerciseRepository
                .findById(result.getExerciseId()).orElseThrow(EntityNotFoundException::new);
        for (ProfileTranslation profileTranslation : profileTranslations) {
            profileTranslation.setRepeatCount(profileTranslation.getRepeatCount() + 1);
            profileTranslation.setSuccessRepeatCount(result.isAnswerCorrect() ?
                    profileTranslation.getSuccessRepeatCount() + 1 : profileTranslation.getSuccessRepeatCount());
            profileTranslation.setLastRepeatDate(new Date());
            profileTranslationRepository.save(profileTranslation);
            exerciseHistoryRepository.save(ExerciseHistory.builder()
                    .profileTranslation(profileTranslation)
                    .exercise(exercise)
                    .result(result.isAnswerCorrect())
                    .date(new Date())
                    .build());
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


    private List<AnswerDto> generateResponseVariants(Set<String> allTranslationValues, Set<String> correct) {
        List<String> incorrectAnswerList = CollectionUtil.createListFromCollectionWithoutElements(allTranslationValues, correct);
        int maxNumberOfIncorrectAnswers = 4;

        if (incorrectAnswerList.size() >= maxNumberOfIncorrectAnswers) {
            int numberOfElementsToDelete = incorrectAnswerList.size() - maxNumberOfIncorrectAnswers;
            CollectionUtil.removeRandomlyFromList(incorrectAnswerList, numberOfElementsToDelete);
        }
        final String correctJoined = String.join(", ", correct);
        List<AnswerDto> answers = createResponseVariantList(correctJoined, incorrectAnswerList);
        Collections.shuffle(answers);

        return answers;
    }


    private List<AnswerDto> createResponseVariantList(String correct, List<String> incorrectAnswers) {
        List<AnswerDto> result = new ArrayList<>();
        result.add(AnswerDto.builder()
                .value(correct)
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
