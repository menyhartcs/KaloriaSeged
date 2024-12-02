package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.*;
import com.szakdolgozat.KaloriaSeged.entity.*;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.repository.ExerciseRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserExerciseLogRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.UserExerciseLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserExerciseLogServiceImplTest {

    private static final Long USER_EXERCISE_LOG_ID = 1L;
    private static final Long EXERCISE_ID = 1L;
    private static final String EXERCISE_NAME = "Running";
    private static final int CALORIE = 1;
    private static final Long USER_ID = 2L;
    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_ROLE = "testrole";
    private static final String USER_NAME = "name";
    private static final String PASSWORD = "testpw";
    private static final String GENDER = "gender";
    private static final int HEIGHT = 180;
    private static final int WEIGHT = 75;
    private static final int AGE = 25;
    private static final int USER_CALORIE = 2000;
    private static final int USER_PROTEIN = 150;
    private static final int USER_CARBOHYDRATE = 300;
    private static final int USER_FAT = 60;
    private static final List<UserFoodLog> USER_FOOD_LOGS = new ArrayList<>();
    private static final List<UserExerciseLog> USER_EXERCISE_LOGS = new ArrayList<>();
    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();
    private static final LocalDate DATE = LocalDate.of(2024, 11, 18);
    private static final int DURATION = 1;
    private static final User USER = new User(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE,
            USER_FOOD_LOGS, USER_EXERCISE_LOGS);
    private static final UserDto USER_DTO = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE,
            USER_FOOD_LOG_DTOS, USER_EXERCISE_LOG_DTOS);
    private static final Exercise EXERCISE = new Exercise(EXERCISE_ID, EXERCISE_NAME, CALORIE,
            USER_EXERCISE_LOGS);
    private static final ExerciseDto EXERCISE_DTO = new ExerciseDto(EXERCISE_ID, EXERCISE_NAME, CALORIE,
            USER_EXERCISE_LOG_DTOS);

    private UserExerciseLog userExerciseLog;
    private UserExerciseLogDto userExerciseLogDto;

    @Mock
    private UserExerciseLogRepository userExerciseLogRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @InjectMocks
    private UserExerciseLogServiceImpl userExerciseLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userExerciseLog = new UserExerciseLog(USER_EXERCISE_LOG_ID, USER, EXERCISE, DATE, DURATION);
        userExerciseLogDto = new UserExerciseLogDto(USER_EXERCISE_LOG_ID, USER_DTO, EXERCISE_DTO, DATE, DURATION);
    }

    @Test
    void testCreateUserFoodLog() {
        // GIVEN
        when(userExerciseLogRepository.save(any(UserExerciseLog.class))).thenReturn(userExerciseLog);

        // WHEN
        UserExerciseLogDto result = userExerciseLogService.createUserExerciseLog(userExerciseLogDto);

        // THEN
        assertNotNull(result);
        assertEquals(USER_EXERCISE_LOG_ID, result.getId());
        verify(userExerciseLogRepository, times(1)).save(any(UserExerciseLog.class));
    }

    @Test
    void testGetUserFoodLogById_Found() {
        // GIVEN
        when(userExerciseLogRepository.findById(USER_EXERCISE_LOG_ID)).thenReturn(java.util.Optional.of(userExerciseLog));

        // WHEN
        UserExerciseLogDto result = userExerciseLogService.getUserExerciseLogById(USER_EXERCISE_LOG_ID);

        // THEN
        assertNotNull(result);
        assertEquals(USER_EXERCISE_LOG_ID, result.getId());
    }

    @Test
    void testGetUserFoodLogByIdNotFound() {
        // GIVEN
        when(userExerciseLogRepository.findById(USER_EXERCISE_LOG_ID)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () ->
                userExerciseLogService.getUserExerciseLogById(USER_EXERCISE_LOG_ID));
    }

    @Test
    void testGetAllUserFoodLogs() {
        // GIVEN
        when(userExerciseLogRepository.findAll()).thenReturn(Arrays.asList(userExerciseLog));

        // WHEN
        List<UserExerciseLogDto> result = userExerciseLogService.getAllUserExerciseLogs();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userExerciseLogRepository, times(1)).findAll();
    }

    @Test
    void testGetUserFoodLogsByUserIdAndDate() {
        // GIVEN
        when(userExerciseLogRepository.findByUserIdAndDate(USER_ID, DATE)).thenReturn(Arrays.asList(userExerciseLog));

        // WHEN
        List<UserExerciseLogDto> result = userExerciseLogService.getUserExerciseLogsByUserIdAndDate(USER_ID, DATE);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userExerciseLogRepository, times(1)).findByUserIdAndDate(USER_ID, DATE);
    }

    @Test
    void testGetUserFoodLogsByDate() {
        // GIVEN
        when(userExerciseLogRepository.findByDate(DATE)).thenReturn(Arrays.asList(userExerciseLog));

        // WHEN
        List<UserExerciseLogDto> result = userExerciseLogService.getUserExerciseLogsByDate(DATE);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userExerciseLogRepository, times(1)).findByDate(DATE);
    }

    @Test
    void testGetUserFoodLogsByUserId() {
        // GIVEN
        when(userExerciseLogRepository.findByUserId(USER_ID)).thenReturn(Arrays.asList(userExerciseLog));

        // WHEN
        List<UserExerciseLogDto> result = userExerciseLogService.getUserExerciseLogsByUserId(USER_ID);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userExerciseLogRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void testUpdateUserFoodLog() {
        // GIVEN
        Exercise newExercise = new Exercise();
        newExercise.setId(2L);
        ExerciseDto newExerciseDto = new ExerciseDto();
        newExerciseDto.setId(2L);
        userExerciseLogDto.setExercise(newExerciseDto);
        when(userExerciseLogRepository.findById(USER_EXERCISE_LOG_ID)).thenReturn(java.util.Optional.of(userExerciseLog));
        when(exerciseRepository.findById(2L)).thenReturn(java.util.Optional.of(newExercise));
        when(userExerciseLogRepository.save(any(UserExerciseLog.class))).thenReturn(userExerciseLog);

        // WHEN
        UserExerciseLogDto result = userExerciseLogService.updateUserExerciseLog(USER_EXERCISE_LOG_ID, userExerciseLogDto);

        // THEN
        assertNotNull(result);
        assertEquals(newExerciseDto.getId(), result.getExercise().getId());
        verify(userExerciseLogRepository, times(1)).save(any(UserExerciseLog.class));
    }

    @Test
    void testDeleteUserFoodLog() {
        // GIVEN
        when(userExerciseLogRepository.findById(USER_EXERCISE_LOG_ID)).thenReturn(java.util.Optional.of(userExerciseLog));

        // WHEN
        userExerciseLogService.deleteUserExerciseLog(USER_EXERCISE_LOG_ID);

        // THEN
        verify(userExerciseLogRepository, times(1)).deleteById(USER_EXERCISE_LOG_ID);
    }

    @Test
    void testDeleteUserFoodLogNotFound() {
        // GIVEN
        when(userExerciseLogRepository.findById(USER_EXERCISE_LOG_ID)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userExerciseLogService.deleteUserExerciseLog(USER_EXERCISE_LOG_ID));
    }
}
