package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.ExerciseDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Exercise;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.repository.ExerciseRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserExerciseLogRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExerciseServiceImplTest {
    private static final Long EXERCISE_ID = 1L;
    private static final String EXERCISE_NAME = "Running";
    private static final int CALORIE = 1;
    private static final List<UserExerciseLog> USER_EXERCISE_LOGS = new ArrayList<>();
    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();

    private Exercise exercise;
    private ExerciseDto exerciseDto;

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private UserExerciseLogRepository userExerciseLogRepository;
    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        exercise = new Exercise(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOGS);
        exerciseDto = new ExerciseDto(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOG_DTOS);

        // Mocking the repository methods
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.findByName(EXERCISE_NAME)).thenReturn(exercise);
        when(exerciseRepository.findAll()).thenReturn(java.util.Collections.singletonList(exercise));
        when(userExerciseLogRepository.findByExerciseId(EXERCISE_ID)).thenReturn(USER_EXERCISE_LOGS);
    }

    @Test
    void testCreateExercise() {
        // WHEN
        ExerciseDto result = exerciseService.createExercise(exerciseDto);

        // THEN
        assertNotNull(result);
        assertEquals(EXERCISE_ID, result.getId());
        assertEquals(EXERCISE_NAME, result.getName());
        assertEquals(CALORIE, result.getCalorie());

        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    void testGetExerciseById_ExerciseExists() {
        // WHEN
        ExerciseDto result = exerciseService.getExerciseById(EXERCISE_ID);

        // THEN
        assertNotNull(result);
        assertEquals(EXERCISE_ID, result.getId());
        assertEquals(EXERCISE_NAME, result.getName());

        verify(exerciseRepository, times(1)).findById(EXERCISE_ID);
    }

    @Test
    void testGetExerciseById_ExerciseNotFound() {
        // GIVEN
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> exerciseService.getExerciseById(EXERCISE_ID));

        verify(exerciseRepository, times(1)).findById(EXERCISE_ID);
    }

    @Test
    void testGetExerciseByName_ExerciseExists() {
        // WHEN
        ExerciseDto result = exerciseService.getExerciseByName(EXERCISE_NAME);

        // THEN
        assertNotNull(result);
        assertEquals(EXERCISE_NAME, result.getName());

        verify(exerciseRepository, times(1)).findByName(EXERCISE_NAME);
    }

    @Test
    void testGetExerciseByName_ExerciseNotFound() {
        // GIVEN
        when(exerciseRepository.findByName(EXERCISE_NAME)).thenReturn(null);

        // WHEN
        ExerciseDto result = exerciseService.getExerciseByName(EXERCISE_NAME);

        // THEN
        assertNull(result);

        verify(exerciseRepository, times(1)).findByName(EXERCISE_NAME);
    }

    @Test
    void testGetAllExercises() {
        // WHEN
        var result = exerciseService.getAllExercises();

        // THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(exerciseRepository, times(1)).findAll();
    }

    @Test
    void testUpdateExercise() {
        // GIVEN
        ExerciseDto updatedExerciseDto = new ExerciseDto(EXERCISE_ID, "Updated Running", 60, null);
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // WHEN
        ExerciseDto result = exerciseService.updateExercise(EXERCISE_ID, updatedExerciseDto);

        // THEN
        assertNotNull(result);
        assertEquals("Updated Running", result.getName());
        assertEquals(60, result.getCalorie());

        verify(exerciseRepository, times(1)).findById(EXERCISE_ID);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    void testDeleteExercise_ExerciseExists() {
        // WHEN
        exerciseService.deleteExercise(EXERCISE_ID);

        // THEN
        verify(exerciseRepository, times(1)).findById(EXERCISE_ID);
        verify(exerciseRepository, times(1)).deleteById(EXERCISE_ID);
    }

    @Test
    void testDeleteExercise_ExerciseNotFound() {
        // GIVEN
        when(exerciseRepository.findById(EXERCISE_ID)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> exerciseService.deleteExercise(EXERCISE_ID));

        verify(exerciseRepository, times(1)).findById(EXERCISE_ID);
    }
}
