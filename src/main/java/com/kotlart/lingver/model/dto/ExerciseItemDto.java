package com.kotlart.lingver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItemDto {
    Set<Long> profileTranslationIds;
    String question;
    List<AnswerDto> answers;
    Long exerciseId;
    String exerciseKey;
}
