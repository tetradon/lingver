package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileTranslationRepository extends PagingAndSortingRepository<ProfileTranslation, Long> {
    @Query(value = "select pt from ProfileTranslation pt "
                   + "join fetch pt.translation translation "
                   + "join fetch translation.word "
                   + "join fetch pt.profile profile "
                   + "where profile.id = ?1",
            countQuery = "select count(pt) from ProfileTranslation pt where pt.profile.id = ?1")
    Page<ProfileTranslation> findAllByProfileId(Long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from ProfileTranslation pt where pt.id in ?1")
    int deleteAllByIds(List<Long> ids);
}
