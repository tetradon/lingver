package com.kotlart.lingver.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = ExerciseHistory.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class ExerciseHistory extends AbstractEntity {
    public static final String TABLE_NAME = "exercise_history";

    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Boolean result;

    @ManyToOne
    @JoinColumn(name = ProfileTranslation.TABLE_NAME + FK_SUFFIX)
    private ProfileTranslation profileTranslation;

    @ManyToOne
    @JoinColumn(name = Exercise.TABLE_NAME + FK_SUFFIX)
    private Exercise exercise;
}
