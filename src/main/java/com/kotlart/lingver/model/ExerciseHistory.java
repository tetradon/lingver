package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp date;
    private Boolean result;
    @ManyToOne
    @JoinColumn(name = Exercise.TABLE_NAME + FK_SUFFIX)
    private Exercise exercise;


}
