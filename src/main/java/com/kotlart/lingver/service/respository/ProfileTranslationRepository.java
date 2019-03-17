package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileTranslationRepository extends PagingAndSortingRepository<ProfileTranslation, Long> {
    @Query(
            value = "select pt from ProfileTranslation pt "
                    + "join fetch pt.translation translation "
                    + "join fetch translation.word word "
                    + "join fetch pt.profile profile "
                    + "where profile.id = ?1 "
                    + "and (translation.value like ?2% or word.value like ?2%)",
            countQuery = "select count(pt) from ProfileTranslation pt "
                    + "join pt.translation translation "
                    + "join translation.word word "
                    + "where pt.profile.id = ?1 and "
                    + "(translation.value like ?2% or word.value like ?2%)")
    Page<ProfileTranslation> findAllByProfileIdAndByWordValueOrTranslationValue(Long id, String search, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from ProfileTranslation pt where pt.id in ?1")
    int deleteAllByIds(List<Long> ids);

    @Query("select pt.translation.id from ProfileTranslation pt where pt.profile.id = ?1")
    List<Long> findAllTranslationIdsByProfileId(Long profileId);
}
