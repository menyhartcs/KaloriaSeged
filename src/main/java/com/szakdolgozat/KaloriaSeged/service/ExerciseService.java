package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;

import java.util.List;

/**
 * Interface for the {@link ExerciseDto object CRUD operations.
 */
public interface ExerciseService {
    ExerciseDto createExercise(ExerciseDto exerciseDto);
    ExerciseDto getExerciseById(Long exerciseId);
    ExerciseDto getExerciseByName(String name);
    List<ExerciseDto> getAllExercises();
    ExerciseDto updateExercise(Long exerciseId, ExerciseDto updatedExercise);
    void deleteExercise(Long exerciseId);
}
