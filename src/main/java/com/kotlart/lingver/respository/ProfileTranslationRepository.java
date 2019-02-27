package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileTranslationRepository extends JpaRepository<ProfileTranslation, Long> {
    @Query("select pt from ProfileTranslation pt "
           + "join fetch pt.translation translation "
           + "join fetch translation.word "
           + "where pt.profile.id = ?1")
    List<ProfileTranslation> findAllByProfileId(Long id);
}
