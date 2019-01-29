package com.kotlart.lingver.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_translation")
public class UserTranslation {
    private Long id;
    private Timestamp insertDate;
    private String description;
    private String example;
    private Translation translation;
    private User user;

    @Id
    @Column(name = "user_translation_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long userTranslationPk) {
        this.id = userTranslationPk;
    }


    @Column(name = "insert_date")
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }


    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTranslation that = (UserTranslation) o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(insertDate, that.insertDate)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(example, that.example)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (insertDate != null ? insertDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (example != null ? example.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "translation_fk", referencedColumnName = "translation_pk", nullable = false)
    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    @ManyToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "user_pk", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
