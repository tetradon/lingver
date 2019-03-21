package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.ExerciseItem;
import com.kotlart.lingver.model.ResponseVariant;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.service.ExerciseService;
import com.kotlart.lingver.service.respository.ProfileTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
        final Iterable<ProfileTranslation> profileTranslations = profileTranslationRepository.findByIdIn(translationIds);
        final Stream<ProfileTranslation> stream = StreamSupport.stream(profileTranslations.spliterator(), false);

        final List<String> words = stream.map(profileTranslation -> profileTranslation.getTranslation().getValue()).collect(Collectors.toList());

        profileTranslations.forEach(profileTranslation -> {
                    ExerciseItem exerciseItem = new ExerciseItem();
                    exerciseItem.setProfileTranslationId(profileTranslation.getId());
                    exerciseItem.setTrainingItem(profileTranslation.getTranslation().getWord().getValue());
                    exerciseItem.setResponseVariants(generateResponseVariantsForProfileTranslation(words, profileTranslation.getTranslation().getValue()));
                    result.add(exerciseItem);
                }
        );
        return result;
    }

    private List<ResponseVariant> generateResponseVariantsForProfileTranslation(List<String> words, String correct) {
        List<ResponseVariant> result = new ArrayList<>();
        words.forEach(w -> {
            ResponseVariant responseVariant = new ResponseVariant();
            responseVariant.setVariant(w);
            responseVariant.setCorrect(correct.equals(w));
            result.add(responseVariant);
        });
        return result;
    }
}
