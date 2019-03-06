package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByWordValueIgnoreCase(String word);

    @Query("select count(pt) from ProfileTranslation pt where pt.translation.id =?1")
    int countTranslationRating(Long translationId);
}
