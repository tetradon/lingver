package com.kotlart.lingver.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ValueDto {
    @NotNull
    private Long id;
    @NotBlank
    private String value;
}
