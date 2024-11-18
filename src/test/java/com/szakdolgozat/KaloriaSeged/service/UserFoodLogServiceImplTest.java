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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserFoodLogServiceImplTest {

    private static final Long FOOD_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long USER_FOOD_LOG_ID = 1L;
    private static final LocalDate DATE = LocalDate.of(2024, 11, 18);

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

        Food food = new Food();
        food.setId(FOOD_ID);
        User user = new User();
        user.setId(USER_ID);
        userFoodLog = new UserFoodLog();
        userFoodLog.setId(USER_FOOD_LOG_ID);
        userFoodLog.setFood(food);
        userFoodLog.setUser(user);
        userFoodLog.setDate(DATE);
        userFoodLog.setAmount(200);

        FoodDto foodDto = new FoodDto();
        food.setId(FOOD_ID);
        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userFoodLogDto = new UserFoodLogDto();
        userFoodLogDto.setId(USER_FOOD_LOG_ID);
        userFoodLogDto.setFood(foodDto);
        userFoodLogDto.setUser(userDto);
        userFoodLogDto.setDate(DATE);
        userFoodLogDto.setAmount(200);
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
    void testGetUserFoodLogById_NotFound() {
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
    void testDeleteUserFoodLog_NotFound() {
        // GIVEN
        when(userFoodLogRepository.findById(USER_FOOD_LOG_ID)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userFoodLogService.deleteUserFoodLog(USER_FOOD_LOG_ID));
    }
}
