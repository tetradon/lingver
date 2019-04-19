package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileTranslationRepository extends PagingAndSortingRepository<ProfileTranslation, Long> {
    @EntityGraph(value = ProfileTranslation.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Page<ProfileTranslation> findAllByProfileIdAndTranslationWordValueStartsWithIgnoreCase(
            Long id, String word, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from ProfileTranslation pt where pt.id in ?1")
    int deleteAllByIds(List<Long> ids);

    @EntityGraph(value = ProfileTranslation.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<ProfileTranslation> findByIdIn(List<Long> ids);

}
