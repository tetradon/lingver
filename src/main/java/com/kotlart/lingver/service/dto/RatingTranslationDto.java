package com.kotlart.lingver.service.dto;

/*
@Data
public class RatingTranslationDto {
    private Long id;
    private String value;
    private Integer rating;

}*/


public interface RatingTranslationDto {
    Long getId();

    String getValue();

    Integer getRating();
}