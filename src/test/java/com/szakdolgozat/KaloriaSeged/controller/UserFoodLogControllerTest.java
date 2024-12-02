package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.service.UserFoodLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserFoodLogControllerTest {

    // INIT UserFoodLogDto
    private static final List<UserFoodLogDto> EMPTY_USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final List<UserExerciseLogDto> EMPTY_USER_EXERCISE_LOG_DTOS = new ArrayList<>();
    // INIT UserDto for UserFoodLogDto
    private static final Long USER_FOOD_LOG_ID = 1L;
    private static final Long USER_ID = 1L;
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
    private static final UserDto USER_DTO1 =
            new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                    USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE,
                    EMPTY_USER_FOOD_LOG_DTOS, EMPTY_USER_EXERCISE_LOG_DTOS);
    // INIT FoodDto1 for UserFoodLogDto
    private static final Long FOOD_ID = 1L;
    private static final String FOOD_NAME = "Apple";
    private static final int FOOD_CALORIE = 1;
    private static final int FOOD_FAT = 1;
    private static final int FOOD_CARBOHYDRATE = 1;
    private static final int FOOD_PROTEIN = 1;
    private static final FoodDto FOOD_DTO1 =
            new FoodDto(FOOD_ID, FOOD_NAME, FOOD_CALORIE, FOOD_FAT, FOOD_CARBOHYDRATE, FOOD_PROTEIN, EMPTY_USER_FOOD_LOG_DTOS);
    // INIT FoodDto2 for UserFoodLogDto
    private static final Long FOOD_ID2 = 2L;
    private static final String FOOD_NAME2 = "Banana";
    private static final int FOOD_CALORIE2 = 2;
    private static final int FOOD_FAT2 = 2;
    private static final int FOOD_CARBOHYDRATE2 = 2;
    private static final int FOOD_PROTEIN2 = 2;
    private static final FoodDto FOOD_DTO2 =
            new FoodDto(FOOD_ID2, FOOD_NAME2, FOOD_CALORIE2, FOOD_FAT2, FOOD_CARBOHYDRATE2, FOOD_PROTEIN2, EMPTY_USER_FOOD_LOG_DTOS);

    // INIT UserFoodLogDto fields
    private static final LocalDate DATE = LocalDate.of(2024, 11, 18);
    public static final int AMOUNT1 = 150;
    public static final int AMOUNT2 = 150;

    private static final UserFoodLogDto USER_FOOD_LOG_DTO1 =
            new UserFoodLogDto(USER_FOOD_LOG_ID, USER_DTO1, FOOD_DTO1, DATE, AMOUNT1);
    private static final UserFoodLogDto USER_FOOD_LOG_DTO2 =
            new UserFoodLogDto(2L, USER_DTO1, FOOD_DTO2, DATE, AMOUNT2);

    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = Arrays.asList(USER_FOOD_LOG_DTO1, USER_FOOD_LOG_DTO2);

    @Mock
    private UserFoodLogService userFoodLogService;

    @InjectMocks
    private UserFoodLogController userFoodLogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserFoodLog() {
        // GIVEN
        when(userFoodLogService.createUserFoodLog(USER_FOOD_LOG_DTO1)).thenReturn(USER_FOOD_LOG_DTO1);

        // WHEN
        ResponseEntity<UserFoodLogDto> response = userFoodLogController.createUserFoodLog(USER_FOOD_LOG_DTO1);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTO1, response.getBody());
        verify(userFoodLogService, times(1)).createUserFoodLog(USER_FOOD_LOG_DTO1);
    }

    @Test
    void testGetUserFoodLogById() {
        // GIVEN
        when(userFoodLogService.getUserFoodLogById(USER_FOOD_LOG_ID)).thenReturn(USER_FOOD_LOG_DTO1);

        // WHEN
        ResponseEntity<UserFoodLogDto> response = userFoodLogController.getUserFoodLogById(USER_FOOD_LOG_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTO1, response.getBody());
        verify(userFoodLogService, times(1)).getUserFoodLogById(USER_FOOD_LOG_ID);
    }

    @Test
    void testGetUserFoodLogByUserId() {
        // GIVEN
        when(userFoodLogService.getUserFoodLogsByUserId(USER_ID)).thenReturn(USER_FOOD_LOG_DTOS);

        // WHEN
        ResponseEntity<List<UserFoodLogDto>> response = userFoodLogController.getUserFoodLogByUserId(USER_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTOS, response.getBody());
        verify(userFoodLogService, times(1)).getUserFoodLogsByUserId(USER_ID);
    }

    @Test
    void testGetUserFoodLogByUserIdAndDate() {
        // GIVEN
        when(userFoodLogService.getUserFoodLogsByUserIdAndDate(USER_ID, DATE)).thenReturn(USER_FOOD_LOG_DTOS);

        // WHEN
        ResponseEntity<List<UserFoodLogDto>> response = userFoodLogController.getUserFoodLogByDate(USER_ID, DATE);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTOS, response.getBody());
        verify(userFoodLogService, times(1)).getUserFoodLogsByUserIdAndDate(USER_ID, DATE);
    }

    @Test
    void testGetUserFoodLogByDate() {
        // GIVEN
        when(userFoodLogService.getUserFoodLogsByDate(DATE)).thenReturn(USER_FOOD_LOG_DTOS);

        // WHEN
        ResponseEntity<List<UserFoodLogDto>> response = userFoodLogController.getUserFoodLogByDate(DATE);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTOS, response.getBody());
        verify(userFoodLogService, times(1)).getUserFoodLogsByDate(DATE);
    }

    @Test
    void testGetAllUserFoodLogs() {
        // GIVEN
        when(userFoodLogService.getAllUserFoodLogs()).thenReturn(USER_FOOD_LOG_DTOS);

        // WHEN
        ResponseEntity<List<UserFoodLogDto>> response = userFoodLogController.getAllUserFoodLogs();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTOS, response.getBody());
        verify(userFoodLogService, times(1)).getAllUserFoodLogs();
    }

    @Test
    void testUpdateUserFoodLog() {
        // GIVEN
        when(userFoodLogService.updateUserFoodLog(USER_FOOD_LOG_ID, USER_FOOD_LOG_DTO1)).thenReturn(USER_FOOD_LOG_DTO1);

        // WHEN
        ResponseEntity<UserFoodLogDto> response = userFoodLogController.updateUserFoodLog(USER_FOOD_LOG_ID, USER_FOOD_LOG_DTO1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_FOOD_LOG_DTO1, response.getBody());
        verify(userFoodLogService, times(1)).updateUserFoodLog(USER_FOOD_LOG_ID, USER_FOOD_LOG_DTO1);
    }

    @Test
    void testDeleteUserFoodLog() {
        // WHEN
        ResponseEntity<String> response = userFoodLogController.deleteUserFoodLog(USER_FOOD_LOG_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UserFoodLog deleted successfully", response.getBody());
        verify(userFoodLogService, times(1)).deleteUserFoodLog(USER_FOOD_LOG_ID);
    }
}
