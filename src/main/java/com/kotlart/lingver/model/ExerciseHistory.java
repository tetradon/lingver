package com.kotlart.lingver.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "exercise_history", schema = "public", catalog = "lingver")
public class ExerciseHistory {
    private Long id;
    private Timestamp date;
    private Boolean result;
    private Exercise exercise;

    @Id
    @Column(name = "exercise_history_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long exerciseHistoryPk) {
        this.id = exerciseHistoryPk;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "result")
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseHistory that = (ExerciseHistory) o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(result, that.result)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "exercise_fk", referencedColumnName = "exercise_pk", nullable = false)
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
