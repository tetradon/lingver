package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = Exercise.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class Exercise extends AbstractEntity {
    public static final String TABLE_NAME = "exercise";
    @Id
    @Column(name = TABLE_NAME + FK_SUFFIX)
    @GeneratedValue
    private Long id;
    private String name;
}

