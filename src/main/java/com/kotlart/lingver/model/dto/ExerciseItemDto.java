package com.kotlart.lingver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItemDto {
    Long profileTranslationId;
    String question;
    List<AnswerDto> answers;
    Long exerciseId;
}
