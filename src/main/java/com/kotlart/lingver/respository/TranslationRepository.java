package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    List<Translation> findByWordValueIgnoreCase(String word);
}
