package com.kotlart.lingver.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = Exercise.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class Exercise extends AbstractEntity {
    public static final String TABLE_NAME = "exercise";

    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Name name;

    public enum Name {
        WORD_TRANSLATION("Word-Translation"),
        TRANSLATION_WORD("Translation-Word");

        private String displayName;

        Name(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

