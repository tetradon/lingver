package com.kotlart.lingver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItem {
    Long profileTranslationId;
    String question;
    List<Answer> answers;
}
