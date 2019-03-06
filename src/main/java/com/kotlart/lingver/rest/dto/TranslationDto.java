package com.kotlart.lingver.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TranslationDto extends ValueDto {
    private ValueDto word;
    private int rating;
}
