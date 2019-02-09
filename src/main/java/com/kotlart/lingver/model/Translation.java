package com.kotlart.lingver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String insertUser;
    private Timestamp insertDate;

    @ManyToOne
    @JoinColumn(name = Word.TABLE_NAME + FK_SUFFIX)
    @JsonBackReference
    private Word word;
}
