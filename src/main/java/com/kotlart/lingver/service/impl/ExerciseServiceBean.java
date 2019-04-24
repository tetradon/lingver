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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<ExerciseItemDto> prepareExercise(List<Long> profileTranslationIds, Exercise.Name exerciseName) {
        final List<ExerciseItemDto> exerciseList = new ArrayList<>();

        List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(profileTranslationIds);
        Exercise exercise = exerciseRepository.findByName(Exercise.Name.WORD_TRANSLATION);

        for (ProfileTranslation profileTranslation : profileTranslations) {
            String question = getQuestion(profileTranslation, exerciseName);
            if (!isQuestionPresentInExercise(exerciseList, question)) {
                Set<String> allPossibleAnswers = getAnswers(profileTranslations, exerciseName);
                Set<ProfileTranslation> profileTranslationsWithSameQuestion
                        = findProfileTranslationWithSameQuestions(profileTranslations, question, exerciseName);
                Set<String> correctAnswers = getAnswers(profileTranslationsWithSameQuestion, exerciseName);
                Set<Long> idsOfProfileTranslationWithSameQuestions = profileTranslationsWithSameQuestion
                        .stream().map(ProfileTranslation::getId).collect(Collectors.toSet());

                ExerciseItemDto exerciseItem = ExerciseItemDto.builder()
                        .profileTranslationIds(idsOfProfileTranslationWithSameQuestions)
                        .question(question)
                        .answers(generateResponseVariants(allPossibleAnswers, correctAnswers))
                        .exerciseId(exercise.getId())
                        .build();
                exerciseList.add(exerciseItem);
            }
        }
        return exerciseList;
    }

    private boolean isQuestionPresentInExercise(List<ExerciseItemDto> exerciseList, String question) {
        return exerciseList.stream().anyMatch(exerciseItem -> exerciseItem.getQuestion().equals(question));
    }

    private String getQuestion(ProfileTranslation profileTranslation, Exercise.Name exerciseName) {
        if (exerciseName.equals(Exercise.Name.WORD_TRANSLATION)) {
            return profileTranslation.getTranslation().getWord().getValue();
        } else {
            return profileTranslation.getTranslation().getValue();
        }
    }

    private Set<String> getAnswers(Collection<ProfileTranslation> profileTranslations, Exercise.Name exerciseName) {
        if (exerciseName.equals(Exercise.Name.WORD_TRANSLATION)) {
            return profileTranslations.stream().map(profileTranslation ->
                    profileTranslation.getTranslation().getValue()).collect(Collectors.toSet());
        } else {
            return profileTranslations.stream().map(profileTranslation ->
                    profileTranslation.getTranslation().getWord().getValue()).collect(Collectors.toSet());
        }
    }

    private Set<ProfileTranslation> findProfileTranslationWithSameQuestions(List<ProfileTranslation> profileTranslations, String question, Exercise.Name exerciseName) {
        return profileTranslations.stream().filter(pt -> getQuestion(pt, exerciseName).equals(question))
                .collect(Collectors.toSet());
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


}
