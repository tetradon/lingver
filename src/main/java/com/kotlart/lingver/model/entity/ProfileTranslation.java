package com.kotlart.lingver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = ProfileTranslation.TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NamedEntityGraph(name = ProfileTranslation.ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = "translation", subgraph = "translationSubgraph"),
                @NamedAttributeNode(value = "profile"),
        }, subgraphs = {
        @NamedSubgraph(name = "translationSubgraph",
                attributeNodes =
                @NamedAttributeNode(value = "word")
        )
}
)

public class ProfileTranslation extends AbstractEntity {
    public static final String TABLE_NAME = "profile_translation";
    public static final String ENTITY_GRAPH = TABLE_NAME + ".detail";

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

    @OneToMany(mappedBy = "profileTranslation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseHistory> exerciseHistory;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastRepeatDate;

    private int repeatCount;
    private int successRepeatCount;


    @PrePersist
    private void fillInsertInfo() {
        setInsertDate(new Date());
    }
}
