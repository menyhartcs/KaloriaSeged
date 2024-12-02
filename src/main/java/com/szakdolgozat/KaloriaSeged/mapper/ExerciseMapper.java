package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;

import java.util.List;

/**
 * Handles the mapping between {@link Exercise} and {@link ExerciseDto} objects.
 */
public class ExerciseMapper {

    // Maps the Exercise to ExerciseDto object.
    public static ExerciseDto mapToExerciseDto(Exercise exercise) {
        List<UserExerciseLogDto> exerciseLogDtos = exercise.getExerciseLogs().stream()
                .map(UserExerciseLogMapper::mapToUserExerciseLogDto)
                .toList();
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getCalorie(),
                exerciseLogDtos
        );
    }

    // Maps the ExerciseDto to Exercise object.
    public static Exercise mapToExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setCalorie(exerciseDto.getCalorie());
        return exercise;
    }
}
