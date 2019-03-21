package com.kotlart.lingver.model;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseItem {
    Long profileTranslationId;
    String trainingItem;
    List<ResponseVariant> responseVariants;
}
