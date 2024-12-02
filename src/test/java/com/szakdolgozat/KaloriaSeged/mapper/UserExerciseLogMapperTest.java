package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.*;
import com.szakdolgozat.KaloriaSeged.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserExerciseLogMapperTest {

    private static final Long USER_FOOD_LOG_ID = 1L;
    private static final Long EXERCISE_ID = 1L;
    private static final String EXERCISE_NAME = "Apple";
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
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE, USER_FOOD_LOGS, USER_EXERCISE_LOGS);
    private static final UserDto USER_DTO = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE, USER_FOOD_LOG_DTOS, USER_EXERCISE_LOG_DTOS);
    private static final Exercise EXERCISE = new Exercise(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOGS);
    private static final ExerciseDto EXERCISE_DTO = new ExerciseDto(EXERCISE_ID, EXERCISE_NAME, CALORIE, USER_EXERCISE_LOG_DTOS);

    private UserExerciseLog userExerciseLog;
    private UserExerciseLogDto userExerciseLogDto;

    @BeforeEach
    void setUp() {
        userExerciseLog = new UserExerciseLog(USER_FOOD_LOG_ID, USER, EXERCISE, DATE, DURATION);
        userExerciseLogDto = new UserExerciseLogDto(USER_FOOD_LOG_ID, USER_DTO, EXERCISE_DTO, DATE, DURATION);
    }

    @Test
    void testMapToUserFoodLogDto() {
        // WHEN
        UserExerciseLogDto result = UserExerciseLogMapper.mapToUserExerciseLogDto(userExerciseLog);

        // THEN
        assertEquals(userExerciseLog.getId(), result.getId());
        assertEquals(userExerciseLog.getUser().getId(), result.getUser().getId());
        assertEquals(userExerciseLog.getExercise().getId(), result.getExercise().getId());
        assertEquals(userExerciseLog.getDuration(), result.getDuration());
        assertEquals(userExerciseLog.getDate(), result.getDate());
    }

    @Test
    void testMapToUserFoodLog() {
        // WHEN
        UserExerciseLog result = UserExerciseLogMapper.mapToUserExerciseLog(userExerciseLogDto);

        // THEN
        assertEquals(userExerciseLogDto.getId(), result.getId());
        assertEquals(userExerciseLogDto.getUser().getId(), result.getUser().getId());
        assertEquals(userExerciseLogDto.getExercise().getId(), result.getExercise().getId());
        assertEquals(userExerciseLogDto.getDuration(), result.getDuration());
        assertEquals(userExerciseLogDto.getDate(), result.getDate());
    }

    @Test
    void testMapToUserFoodLogDtoWithNoTNullDate() {
        // GIVEN: Date is already set in the BeforeEach() for userFoodLog

        // WHEN
        UserExerciseLogDto result = UserExerciseLogMapper.mapToUserExerciseLogDto(userExerciseLog);

        // THEN
        assertEquals(DATE, result.getDate());
    }

    @Test
    void testMapToUserFoodLogDtoWithNullDate() {
        // GIVEN
        userExerciseLog.setDate(null);

        // WHEN
        UserExerciseLogDto result = UserExerciseLogMapper.mapToUserExerciseLogDto(userExerciseLog);

        // THEN
        assertEquals(LocalDate.now(), result.getDate());
    }

    @Test
    void testMapToUserFoodLogWithNotNullDate() {
        // GIVEN: Date is already set in the userFoodLog

        // WHEN
        UserExerciseLog result = UserExerciseLogMapper.mapToUserExerciseLog(userExerciseLogDto);

        // THEN
        assertEquals(DATE, result.getDate());
    }

    @Test
    void testMapToUserFoodLogWithNullDate() {
        // GIVEN
        userExerciseLogDto.setDate(null);

        // WHEN
        UserExerciseLog result = UserExerciseLogMapper.mapToUserExerciseLog(userExerciseLogDto);

        // THEN
        assertEquals(LocalDate.now(), result.getDate());
    }

}
