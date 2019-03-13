package com.kotlart.lingver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = Translation.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Translation extends AbstractEntity {
    public static final String TABLE_NAME = "translation";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String insertedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    @ManyToOne
    @JoinColumn(name = Word.TABLE_NAME + FK_SUFFIX)
    private Word word;

    @PrePersist
    private void fillInsertInfo() {
        setInsertedBy("LINGVER");
        setInsertDate(new Date());
    }

}
