package com.kotlart.lingver.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TranslationDto extends ValueDto {
    private ValueDto word;
}
