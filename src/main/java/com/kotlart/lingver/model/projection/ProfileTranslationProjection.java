package com.kotlart.lingver.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/*@Data
@AllArgsConstructor*/
public interface ProfileTranslationProjection {
  /*  private Long id;
    private Long translationId;
    private String translation;
    private Long wordId;
    private String word;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date insertDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date lastRepeatedDate;
    private int numberOfSuccessRepeating;*/

    Long getId();

    String getTranslation();

    String getWord();
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date getInsertDate();
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date getLastRepeatDate();

    int getNumberOfSuccessRepeating();
}
