package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Answer;
import com.kotlart.lingver.model.ExerciseItem;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.service.ExerciseService;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceBean implements ExerciseService {

    private final ProfileTranslationRepository profileTranslationRepository;

    @Autowired
    public ExerciseServiceBean(ProfileTranslationRepository profileTranslationRepository) {
        this.profileTranslationRepository = profileTranslationRepository;
    }

    @Override
    public List<ExerciseItem> generateWordTranslationTrainingSet(List<Long> translationIds) {
        final List<ExerciseItem> result = new ArrayList<>();
        final List<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(translationIds);

        final List<String> translations = profileTranslations
                .stream()
                .map(profileTranslation -> profileTranslation.getTranslation().getValue())
                .collect(Collectors.toList());

        profileTranslations.forEach(profileTranslation -> {
                    ExerciseItem exerciseItem = new ExerciseItem();
                    exerciseItem.setProfileTranslationId(profileTranslation.getId());
            exerciseItem.setQuestionText(profileTranslation.getTranslation().getWord().getValue());
            exerciseItem.setAnswers(generateResponseVariants(translations, profileTranslation.getTranslation().getValue()));
                    result.add(exerciseItem);
                }
        );
        return result;
    }

    private List<Answer> generateResponseVariants(List<String> allTranslationValues, String correct) {
        List<String> incorrectAnswers = createIncorrectAnswerList(allTranslationValues, correct);
        int maxNumberOfIncorrectAnswers = 4;

        if (incorrectAnswers.size() >= maxNumberOfIncorrectAnswers) {
            int numberOfElementsToDelete = incorrectAnswers.size() - maxNumberOfIncorrectAnswers;
            removeRandomlyFromList(incorrectAnswers, numberOfElementsToDelete);
        }

        List<Answer> answers = createResponseVariantList(correct, incorrectAnswers);
        Collections.shuffle(answers);

        return answers;
    }

    private void removeRandomlyFromList(List<String> list, int numberOfElementsToDelete) {
        final Random random = new Random();
        for (int i = 0; i < numberOfElementsToDelete; i++) {
            list.remove(random.nextInt(list.size()));
        }
    }

    private List<String> createIncorrectAnswerList(List<String> allTranslationValues, String correct) {
        List<String> translationVariants = new ArrayList<>(allTranslationValues);
        translationVariants.remove(correct);
        return translationVariants;
    }

    private List<Answer> createResponseVariantList(String correctAnswer, List<String> incorrectAnswers) {
        List<Answer> result = new ArrayList<>();
        result.add(Answer.builder()
                .answerText(correctAnswer)
                .correct(true)
                .build());
        incorrectAnswers.forEach(v ->
                result.add(Answer.builder()
                        .answerText(v)
                        .correct(false)
                        .build())
        );
        return result;
    }
}
