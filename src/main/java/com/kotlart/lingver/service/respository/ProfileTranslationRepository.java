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
    @Query(
            value = "select pt from ProfileTranslation pt "
                    + "where pt.profile.id = ?1 "
                    + "and (pt.translation.value like ?2% or pt.translation.word.value like ?2%)")
    @EntityGraph(value = ProfileTranslation.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Page<ProfileTranslation> findAllByProfileIdAndByWordValueOrTranslationValue(Long id, String search, Pageable pageable);


    /*    @Query(value = "select pt.profile_translation_pk                                as id, "
                       + "       t.value                                                as translation, "
                       + "       t.translation_pk                                       as translationId, "
                       + "       w.value                                                as word, "
                       + "       w.word_pk                                              as wordId, "
                       + "       pt.insert_date                                         as insertDate, "
                       + "       count(case when eh.result = true then 1 ELSE NULL END) as numberOfSuccessRepeating, "
                       + "       max(eh.date)                                           as lastRepeatedDate "
                       + "from profile_translation pt "
                       + "       join translation t on t.translation_pk = pt.translation_fk "
                       + "       join word w on t.word_fk = w.word_pk "
                       + "       left join exercise_history eh on pt.profile_translation_pk = eh.profile_translation_fk "
                       + "where pt.profile_fk = ?1 "
                       + "and t.value like ?2 or w.value like ?2 "
                       + "group by id, translationId, wordId ",

                countQuery = "select count(pt.profile_translation_pk) from profile_translation pt "
                             + "       join translation t on t.translation_pk = pt.translation_fk "
                             + "       join word w on t.word_fk = w.word_pk "
                             + "where pt.profile_fk = ?1 and "
                             + "(t.value like ?2 or w.value like ?2)",
                nativeQuery = true)

        Page<ProfileTranslationFlatDto> findAllByProfileIdAndByWordValueOrTranslationValue(Long id, String search, Pageable pageable);*/
    @Transactional
    @Modifying
    @Query("delete from ProfileTranslation pt where pt.id in ?1")
    int deleteAllByIds(List<Long> ids);

    @EntityGraph(value = ProfileTranslation.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<ProfileTranslation> findByIdIn(List<Long> ids);

}
