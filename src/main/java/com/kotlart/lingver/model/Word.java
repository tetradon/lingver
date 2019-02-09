package com.kotlart.lingver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = Word.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
public class Word extends AbstractEntity {
    public static final String TABLE_NAME = "word";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private Timestamp insertDate;
    private String insertUser;

    @OneToMany(mappedBy = Word.TABLE_NAME, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Translation> translations;
}
