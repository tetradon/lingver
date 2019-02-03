package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = Translation.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class Translation extends AbstractEntity {
    public static final String TABLE_NAME = "translation";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue
    private Long id;
    private String value;
    private String insertUser;
    private Timestamp insertDate;

    @ManyToOne
    @JoinColumn(name = Word.TABLE_NAME + FK_SUFFIX)
    private Word word;
}
