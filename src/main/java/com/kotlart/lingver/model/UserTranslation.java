package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = UserTranslation.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class UserTranslation extends AbstractEntity {
    public static final String TABLE_NAME = "user_translation";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp insertDate;
    private String description;
    private String example;

    @ManyToOne
    @JoinColumn(name = Translation.TABLE_NAME + FK_SUFFIX)
    private Translation translation;

    @ManyToOne
    @JoinColumn(name = User.TABLE_NAME + FK_SUFFIX)
    private User user;
}
