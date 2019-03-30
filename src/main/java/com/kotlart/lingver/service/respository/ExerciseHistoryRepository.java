package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExerciseHistoryRepository extends JpaRepository<Profile, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into exercise_history(date, profile_translation_fk, exercise_fk, result) values (current_timestamp, ?1, ?2, ?3)", nativeQuery = true)
    void saveExerciseResult(Long profileTranslationId, Long exerciseId, Boolean result);

    @Transactional
    @Modifying
    @Query("delete from ExerciseHistory eh where eh.profileTranslation.id in ?1")
    int deleteByProfileTranslationIds(List<Long> ids);
}
