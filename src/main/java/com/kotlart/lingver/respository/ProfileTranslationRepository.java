package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileTranslationRepository extends PagingAndSortingRepository<ProfileTranslation, Long> {
    @Query("select pt from ProfileTranslation pt "
           + "join pt.translation translation "
           + "join translation.word "
           + "where pt.profile.id = ?1")
    Page<ProfileTranslation> findAllByProfileId(Long id, Pageable pageable);
}
