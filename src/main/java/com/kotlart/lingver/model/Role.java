package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity
@Table(name = Role.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractEntity {
    public static final String TABLE_NAME = "role";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
