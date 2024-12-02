package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExerciseControllerTest {

    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();
    // INIT ExerciseDto1
    private static final Long EXERCISE_ID = 1L;
    private static final String EXERCISE_NAME = "Running";
    private static final int CALORIE = 1;
    // INIT ExerciseDto2
    private static final Long EXERCISE_ID2 = 2L;
    private static final String EXERCISE_NAME2 = "Walking";
    private static final int CALORIE2 = 2;
    // INIT ExerciseDtos
    private static final ExerciseDto EXERCISE_DTO1 =
            new ExerciseDto(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOG_DTOS);
    private static final ExerciseDto EXERCISE_DTO2 =
            new ExerciseDto(EXERCISE_ID2, EXERCISE_NAME2, CALORIE2, USER_EXERCISE_LOG_DTOS);

    private static final List<ExerciseDto> EXERCISES = Arrays.asList(EXERCISE_DTO1, EXERCISE_DTO2);

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private ExerciseController exerciseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExercise() {
        // GIVEN
        when(exerciseService.createExercise(EXERCISE_DTO1)).thenReturn(EXERCISE_DTO1);

        // WHEN
        ResponseEntity<ExerciseDto> response = exerciseController.createExercise(EXERCISE_DTO1);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(EXERCISE_DTO1, response.getBody());
        verify(exerciseService, times(1)).createExercise(EXERCISE_DTO1);
    }

    @Test
    void testGetExerciseById() {
        // GIVEN
        when(exerciseService.getExerciseById(EXERCISE_ID)).thenReturn(EXERCISE_DTO1);

        // WHEN
        ResponseEntity<ExerciseDto> response = exerciseController.getExerciseById(EXERCISE_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EXERCISE_DTO1, response.getBody());
        verify(exerciseService, times(1)).getExerciseById(EXERCISE_ID);
    }

    @Test
    void testGetExerciseByName() {
        // GIVEN
        when(exerciseService.getExerciseByName(EXERCISE_NAME)).thenReturn(EXERCISE_DTO1);

        // WHEN
        ResponseEntity<ExerciseDto> response = exerciseController.getExerciseById(EXERCISE_NAME);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EXERCISE_DTO1, response.getBody());
        verify(exerciseService, times(1)).getExerciseByName(EXERCISE_NAME);
    }

    @Test
    void testGetAllExercises() {
        // GIVEN
        when(exerciseService.getAllExercises()).thenReturn(EXERCISES);

        // WHEN
        ResponseEntity<List<ExerciseDto>> response = exerciseController.getAllExercises();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EXERCISES, response.getBody());
        verify(exerciseService, times(1)).getAllExercises();
    }

    @Test
    void testUpdateExercise() {
        // GIVEN
        when(exerciseService.updateExercise(EXERCISE_ID, EXERCISE_DTO1)).thenReturn(EXERCISE_DTO1);

        // WHEN
        ResponseEntity<ExerciseDto> response = exerciseController.updateExercise(EXERCISE_ID, EXERCISE_DTO1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EXERCISE_DTO1, response.getBody());
        verify(exerciseService, times(1)).updateExercise(EXERCISE_ID, EXERCISE_DTO1);
    }

    @Test
    void testDeleteExercise() {
        // WHEN
        ResponseEntity<String> response = exerciseController.deleteExercise(EXERCISE_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Exercise deleted successfully", response.getBody());
        verify(exerciseService, times(1)).deleteExercise(EXERCISE_ID);
    }
}
