package com.szakdolgozat.KaloriaSeged.repository;

import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the {@link Exercise} object.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findByName(String name);
}
