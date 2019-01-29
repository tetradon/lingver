package com.kotlart.lingver.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Exercise {
    private Long id;
    private String name;

    @Id
    @Column(name = "exercise_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long exercisePk) {
        this.id = exercisePk;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (!Objects.equals(id, exercise.id)) return false;
        if (!Objects.equals(name, exercise.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
