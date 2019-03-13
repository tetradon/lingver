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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = ProfileTranslation.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProfileTranslation extends AbstractEntity {
    public static final String TABLE_NAME = "profile_translation";

    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;

    private String description;
    private String example;

    @ManyToOne
    @JoinColumn(name = Translation.TABLE_NAME + FK_SUFFIX)
    @NotNull
    private Translation translation;

    @ManyToOne
    @JoinColumn(name = Profile.TABLE_NAME + FK_SUFFIX)
    @NotNull
    private Profile profile;

    @PrePersist
    private void fillInsertInfo() {
        setInsertDate(new Date());
    }
}
