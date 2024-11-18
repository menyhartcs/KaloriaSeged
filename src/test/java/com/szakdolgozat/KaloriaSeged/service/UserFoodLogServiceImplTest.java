package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.repository.FoodRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.UserFoodLogServiceImpl;
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

public class UserFoodLogServiceImplTest {

    private static final Long USER_FOOD_LOG_ID = 1L;
    private static final Long FOOD_ID = 1L;
    private static final String FOOD_NAME = "Apple";
    private static final int CALORIE = 1;
    private static final int FAT = 1;
    private static final int CARBOHYDRATE = 1;
    private static final int PROTEIN = 1;
    private static final Long USER_ID = 2L;
    private static final String USER_EMAIL = "test@mail.com";
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
    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final LocalDate DATE = LocalDate.of(2024, 11, 18);
    private static final int AMOUNT = 100;
    private static final User USER = new User(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOGS);
    private static final UserDto USER_DTO = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOG_DTOS);
    private static final Food FOOD = new Food(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOGS);
    private static final FoodDto FOOD_DTO = new FoodDto(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOG_DTOS);

    private UserFoodLog userFoodLog;
    private UserFoodLogDto userFoodLogDto;

    @Mock
    private UserFoodLogRepository userFoodLogRepository;
    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private UserFoodLogServiceImpl userFoodLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userFoodLog = new UserFoodLog(USER_FOOD_LOG_ID, USER, FOOD, DATE, AMOUNT);
        userFoodLogDto = new UserFoodLogDto(USER_FOOD_LOG_ID, USER_DTO, FOOD_DTO, DATE, AMOUNT);
    }

    @Test
    void testCreateUserFoodLog() {
        // GIVEN
        when(userFoodLogRepository.save(any(UserFoodLog.class))).thenReturn(userFoodLog);

        // WHEN
        UserFoodLogDto result = userFoodLogService.createUserFoodLog(userFoodLogDto);

        // THEN
        assertNotNull(result);
        assertEquals(USER_FOOD_LOG_ID, result.getId());
        verify(userFoodLogRepository, times(1)).save(any(UserFoodLog.class));
    }

    @Test
    void testGetUserFoodLogById_Found() {
        // GIVEN
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.of(userFoodLog));

        // WHEN
        UserFoodLogDto result = userFoodLogService.getUserFoodLogById(USER_FOOD_LOG_ID);

        // THEN
        assertNotNull(result);
        assertEquals(USER_FOOD_LOG_ID, result.getId());
    }

    @Test
    void testGetUserFoodLogByIdNotFound() {
        // GIVEN
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userFoodLogService.getUserFoodLogById(USER_FOOD_LOG_ID));
    }

    @Test
    void testGetAllUserFoodLogs() {
        // GIVEN
        when(userFoodLogRepository.findAll()).thenReturn(Arrays.asList(userFoodLog));

        // WHEN
        List<UserFoodLogDto> result = userFoodLogService.getAllUserFoodLogs();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userFoodLogRepository, times(1)).findAll();
    }

    @Test
    void testGetUserFoodLogsByUserIdAndDate() {
        // GIVEN
        when(userFoodLogRepository.findByUserIdAndDate(USER_ID, DATE)).thenReturn(Arrays.asList(userFoodLog));

        // WHEN
        List<UserFoodLogDto> result = userFoodLogService.getUserFoodLogsByUserIdAndDate(USER_ID, DATE);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userFoodLogRepository, times(1)).findByUserIdAndDate(USER_ID, DATE);
    }

    @Test
    void testGetUserFoodLogsByDate() {
        // GIVEN
        when(userFoodLogRepository.findByDate(DATE)).thenReturn(Arrays.asList(userFoodLog));

        // WHEN
        List<UserFoodLogDto> result = userFoodLogService.getUserFoodLogsByDate(DATE);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userFoodLogRepository, times(1)).findByDate(DATE);
    }

    @Test
    void testGetUserFoodLogsByUserId() {
        // GIVEN
        when(userFoodLogRepository.findByUserId(USER_ID)).thenReturn(Arrays.asList(userFoodLog));

        // WHEN
        List<UserFoodLogDto> result = userFoodLogService.getUserFoodLogsByUserId(USER_ID);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userFoodLogRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    void testUpdateUserFoodLog() {
        // GIVEN
        Food newFood = new Food();
        newFood.setId(2L);
        FoodDto newFoodDto = new FoodDto();
        newFoodDto.setId(2L);
        userFoodLogDto.setFood(newFoodDto);
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.of(userFoodLog));
        when(foodRepository.findById(2L)).thenReturn(java.util.Optional.of(newFood));
        when(userFoodLogRepository.save(any(UserFoodLog.class))).thenReturn(userFoodLog);

        // WHEN
        UserFoodLogDto result = userFoodLogService.updateUserFoodLog(USER_FOOD_LOG_ID, userFoodLogDto);

        // THEN
        assertNotNull(result);
        assertEquals(newFood.getId(), result.getFood().getId());
        verify(userFoodLogRepository, times(1)).save(any(UserFoodLog.class));
    }

    @Test
    void testDeleteUserFoodLog() {
        // GIVEN
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.of(userFoodLog));

        // WHEN
        userFoodLogService.deleteUserFoodLog(USER_FOOD_LOG_ID);

        // THEN
        verify(userFoodLogRepository, times(1)).deleteById(USER_FOOD_LOG_ID);
    }

    @Test
    void testDeleteUserFoodLogNotFound() {
        // GIVEN
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userFoodLogService.deleteUserFoodLog(USER_FOOD_LOG_ID));
    }
}
