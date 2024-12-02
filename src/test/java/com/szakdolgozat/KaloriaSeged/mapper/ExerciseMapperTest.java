package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseMapperTest {

    private static final Long EXERCISE_ID = 1L;
    private static final String EXERCISE_NAME = "Running";
    private static final int CALORIE = 1;
    private static final List<UserExerciseLog> USER_EXERCISE_LOGS = new ArrayList<>();
    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();

    private Exercise exercise;
    private ExerciseDto exerciseDto;

    @BeforeEach
    void setUp() {
        exercise = new Exercise(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOGS);
        exerciseDto = new ExerciseDto(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOG_DTOS);
    }

    @Test
    void testMapToExerciseDto() {
        // WHEN
        ExerciseDto result = ExerciseMapper.mapToExerciseDto(exercise);

        // THEN
        assertEquals(exercise.getId(), result.getId());
        assertEquals(exercise.getName(), result.getName());
        assertEquals(exercise.getCalorie(), result.getCalorie());
        assertEquals(exercise.getExerciseLogs().size(), result.getExerciseLogs().size());
    }

    @Test
    void testMapToExercise() {
        // WHEN
        Exercise result = ExerciseMapper.mapToExercise(exerciseDto);

        // THEN
        assertEquals(exerciseDto.getId(), result.getId());
        assertEquals(exerciseDto.getName(), result.getName());
        assertEquals(exerciseDto.getCalorie(), result.getCalorie());
    }
}
