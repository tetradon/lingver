package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findByName(Exercise.Name name);
}
