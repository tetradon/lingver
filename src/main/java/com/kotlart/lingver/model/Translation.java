package com.kotlart.lingver.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Translation {
    private Long id;
    private String value;
    private String insertUser;
    private Timestamp insertDate;

    @Id
    @Column(name = "translation_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long translationPk) {
        this.id = translationPk;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "insert_user")
    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    @Basic
    @Column(name = "insert_date")
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translation that = (Translation) o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(insertUser, that.insertUser)) return false;
        if (!Objects.equals(insertDate, that.insertDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (insertUser != null ? insertUser.hashCode() : 0);
        result = 31 * result + (insertDate != null ? insertDate.hashCode() : 0);
        return result;
    }
}
