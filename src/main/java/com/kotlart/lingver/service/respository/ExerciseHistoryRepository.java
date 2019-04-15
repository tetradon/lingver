package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.ExerciseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExerciseHistoryRepository extends JpaRepository<ExerciseHistory, Long> {

    @Transactional
    @Modifying
    @Query("delete from ExerciseHistory eh where eh.profileTranslation.id in ?1")
    int deleteByProfileTranslationIds(List<Long> ids);
}
