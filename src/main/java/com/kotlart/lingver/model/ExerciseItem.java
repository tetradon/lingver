package com.kotlart.lingver.model;

import lombok.Data;

import java.util.List;

@Data
public class ExerciseItem {
    Long profileTranslationId;
    String questionText;
    List<Answer> answers;
}
