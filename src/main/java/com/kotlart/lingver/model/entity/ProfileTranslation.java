package com.kotlart.lingver.model.entity;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
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
@NamedEntityGraph(name = ProfileTranslation.DETAIL_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = "translation", subgraph = "translationSubgraph"),
                @NamedAttributeNode(value = "profile", subgraph = "profileSubgraph")
        }, subgraphs = {
        @NamedSubgraph(name = "translationSubgraph",
                attributeNodes = {
                        @NamedAttributeNode(value = "word")
                }),
        @NamedSubgraph(name = "profileSubgraph",
                attributeNodes = {
                        @NamedAttributeNode(value = "authorities")
                })
}

)

public class ProfileTranslation extends AbstractEntity {
    public static final String TABLE_NAME = "profile_translation";
    public static final String DETAIL_ENTITY_GRAPH = TABLE_NAME + ".detail";

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
