package com.kotlart.lingver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedProfileTranslationsDto {
    List<ProfileTranslationDto> translations;
    List<Long> allTranslationIds;
}
