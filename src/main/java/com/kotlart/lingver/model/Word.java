package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
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

    @Temporal(TemporalType.DATE)
    private Date insertDate;
    private String insertedBy;

    @OneToMany(mappedBy = Word.TABLE_NAME, cascade = CascadeType.ALL)
    private List<Translation> translations;

    @PrePersist
    private void fillInsertInfo() {
        setInsertedBy("LINGVER");
        setInsertDate(new Date());
    }
}
