package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
    Word findByValue(String value);
}
