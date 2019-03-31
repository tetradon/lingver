package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.projection.ProfileTranslationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileTranslationRepository extends PagingAndSortingRepository<ProfileTranslation, Long> {

    @Query(value = "select pt.profile_translation_pk                              as id, "
                   + "       t.value                                                as translation, "
                   + "       w.value                                                as word, "
                   + "       pt.insert_date                                         as insertDate, "
                   + "       count(case when eh.result = true then 1 else null end) as numberOfSuccessRepeating, "
                   + "       coalesce(max(eh.date), date '01.01.1900')                                           as lastRepeatDate "
                   + "from profile_translation pt "
                   + "       join translation t on t.translation_pk = pt.translation_fk "
                   + "       join word w on t.word_fk = w.word_pk "
                   + "       left join exercise_history eh on pt.profile_translation_pk = eh.profile_translation_fk "
                   + "where pt.profile_fk = ?1 "
                   + "and (t.value like concat(?2,'%') or w.value like concat(?2,'%')) "
                   + "group by id, translation, word",


            countQuery = "select count(pt.profile_translation_pk) from profile_translation pt "
                         + "       join translation t on t.translation_pk = pt.translation_fk "
                         + "       join word w on t.word_fk = w.word_pk "
                         + "where pt.profile_fk = ?1 and  "
                         + "(t.value like concat(?2,'%') or w.value like concat(?2,'%'))",
            nativeQuery = true)
    Page<ProfileTranslationProjection> findAllByProfileIdAndByWordValueOrTranslationValue(Long id, String search, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from ProfileTranslation pt where pt.id in ?1")
    int deleteAllByIds(List<Long> ids);

    @EntityGraph(value = ProfileTranslation.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<ProfileTranslation> findByIdIn(List<Long> ids);

}
