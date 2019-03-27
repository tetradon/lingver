package com.kotlart.lingver.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface ProfileTranslationProjection {

    Long getId();
    String getTranslation();
    String getWord();
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date getInsertDate();
    @JsonFormat(pattern = "dd.MM.yyyy")
    Date getLastRepeatDate();
    int getNumberOfSuccessRepeating();
}
