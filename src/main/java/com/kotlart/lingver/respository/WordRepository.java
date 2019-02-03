package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
