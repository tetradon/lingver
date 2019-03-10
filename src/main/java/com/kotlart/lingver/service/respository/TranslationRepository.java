package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.Translation;
import com.kotlart.lingver.service.dto.RatingTranslationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query(value = "select translation.translation_pk as id, translation.value as value, count(profile_translation.profile_fk) as rating from translation " +
            "join word on translation.word_fk = word.word_pk " +
            "left join profile_translation on translation.translation_pk = profile_translation.translation_fk " +
            "where upper(word.value) = upper(?1) " +
            "group by translation.translation_pk, translation.value " +
            "order by rating desc",
            nativeQuery = true)
    List<RatingTranslationDto> findByWordValueIgnoreCase(String word);
}
