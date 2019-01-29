package com.kotlart.lingver.respoitory;

import com.kotlart.lingver.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
