package com.kotlart.lingver.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = TABLE_NAME + FK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

