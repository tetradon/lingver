package com.kotlart.lingver.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Word {
    private Long id;
    private String value;
    private Timestamp insertDate;
    private String insertUser;

    @Id
    @Column(name = "word_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Column(name = "insert_date")
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    @Column(name = "insert_user")
    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!Objects.equals(id, word.id)) return false;
        if (!Objects.equals(value, word.value)) return false;
        if (!Objects.equals(insertDate, word.insertDate)) return false;
        return Objects.equals(insertUser, word.insertUser);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (insertDate != null ? insertDate.hashCode() : 0);
        result = 31 * result + (insertUser != null ? insertUser.hashCode() : 0);
        return result;
    }
}
