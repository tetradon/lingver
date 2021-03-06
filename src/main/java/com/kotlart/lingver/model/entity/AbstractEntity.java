package com.kotlart.lingver.model.entity;


public abstract class AbstractEntity {
    public static final String FK_SUFFIX = "_fk";
    public static final String PK_SUFFIX = "_pk";

    public abstract Long getId();

    public abstract void setId(Long id);
}
