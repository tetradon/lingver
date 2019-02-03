package com.kotlart.lingver.model;

public abstract class AbstractEntity {
    public static final String FK_SUFFIX = "_fk";
    public static final String PK_SUFFIX = "_pk";

    abstract public Long getId();

    abstract public void setId(Long id);
}
