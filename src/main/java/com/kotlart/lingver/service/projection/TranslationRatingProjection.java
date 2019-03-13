package com.kotlart.lingver.service.projection;

/*
@Data
public class TranslationRatingProjection {
    private Long id;
    private String value;
    private Integer rating;

}*/


public interface TranslationRatingProjection {
    Long getId();
    String getValue();

    int getRating();
}