package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.Answer;
import com.kotlart.lingver.model.ExerciseItem;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.service.ExerciseService;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import com.kotlart.lingver.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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

        final Set<String> translations = profileTranslations
                .stream()
                .map(profileTranslation -> profileTranslation.getTranslation().getValue())
                .collect(Collectors.toSet());

        profileTranslations.forEach(profileTranslation -> {
            ExerciseItem exerciseItem = ExerciseItem.builder()
                    .profileTranslationId(profileTranslation.getId())
                    .question(profileTranslation.getTranslation().getWord().getValue())
                    .answers(generateResponseVariants(translations, profileTranslation.getTranslation().getValue()))
                    .build();
                    result.add(exerciseItem);
                }
        );
        return result;
    }

    private List<Answer> generateResponseVariants(Set<String> allTranslationValues, String correct) {
        List<String> incorrectAnswerList = CollectionUtil.createListFromCollectionWithoutElement(allTranslationValues, correct);
        int maxNumberOfIncorrectAnswers = 4;

        if (incorrectAnswerList.size() >= maxNumberOfIncorrectAnswers) {
            int numberOfElementsToDelete = incorrectAnswerList.size() - maxNumberOfIncorrectAnswers;
            CollectionUtil.removeRandomlyFromList(incorrectAnswerList, numberOfElementsToDelete);
        }

        List<Answer> answers = createResponseVariantList(correct, incorrectAnswerList);
        Collections.shuffle(answers);

        return answers;
    }


    private List<Answer> createResponseVariantList(String correctAnswer, List<String> incorrectAnswers) {
        List<Answer> result = new ArrayList<>();
        result.add(Answer.builder()
                .value(correctAnswer)
                .isCorrect(true)
                .build());
        incorrectAnswers.forEach(v ->
                result.add(Answer.builder()
                        .value(v)
                        .isCorrect(false)
                        .build())
        );
        return result;
    }
}
