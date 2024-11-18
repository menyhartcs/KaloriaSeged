package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.repository.FoodRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.FoodServiceImpl;
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

public class FoodServiceImplTest {
    private static final Long FOOD_ID = 1L;
    private static final String FOOD_NAME = "Apple";
    private static final int CALORIE = 1;
    private static final int FAT = 1;
    private static final int CARBOHYDRATE = 1;
    private static final int PROTEIN = 1;
    private static final List<UserFoodLog> USER_FOOD_LOGS = new ArrayList<>();
    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();

    private Food food;
    private FoodDto foodDto;

    @Mock
    private FoodRepository foodRepository;
    @Mock
    private UserFoodLogRepository userFoodLogRepository;
    @InjectMocks
    private FoodServiceImpl foodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        food = new Food(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOGS);
        foodDto = new FoodDto(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOG_DTOS);

        // Mocking the repository methods
        when(foodRepository.save(any(Food.class))).thenReturn(food);
        when(foodRepository.findById(FOOD_ID)).thenReturn(Optional.of(food));
        when(foodRepository.findByName(FOOD_NAME)).thenReturn(food);
        when(foodRepository.findAll()).thenReturn(java.util.Collections.singletonList(food));
        when(userFoodLogRepository.findByFoodId(FOOD_ID)).thenReturn(USER_FOOD_LOGS);
    }

    @Test
    void testCreateFood() {
        // WHEN
        FoodDto result = foodService.createFood(foodDto);

        // THEN
        assertNotNull(result);
        assertEquals(FOOD_ID, result.getId());
        assertEquals(FOOD_NAME, result.getName());
        assertEquals(CALORIE, result.getCalorie());
        assertEquals(FAT, result.getFat());
        assertEquals(CARBOHYDRATE, result.getCarbohydrate());
        assertEquals(PROTEIN, result.getProtein());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void testGetFoodById_FoodExists() {
        // WHEN
        FoodDto result = foodService.getFoodById(FOOD_ID);

        // THEN
        assertNotNull(result);
        assertEquals(FOOD_ID, result.getId());
        assertEquals(FOOD_NAME, result.getName());

        verify(foodRepository, times(1)).findById(FOOD_ID);
    }

    @Test
    void testGetFoodById_FoodNotFound() {
        // GIVEN
        when(foodRepository.findById(FOOD_ID)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> foodService.getFoodById(FOOD_ID));

        verify(foodRepository, times(1)).findById(FOOD_ID);
    }

    @Test
    void testGetFoodByName_FoodExists() {
        // WHEN
        FoodDto result = foodService.getFoodByName(FOOD_NAME);

        // THEN
        assertNotNull(result);
        assertEquals(FOOD_NAME, result.getName());

        verify(foodRepository, times(1)).findByName(FOOD_NAME);
    }

    @Test
    void testGetFoodByName_FoodNotFound() {
        // GIVEN
        when(foodRepository.findByName(FOOD_NAME)).thenReturn(null);

        // WHEN
        FoodDto result = foodService.getFoodByName(FOOD_NAME);

        // THEN
        assertNull(result);

        verify(foodRepository, times(1)).findByName(FOOD_NAME);
    }

    @Test
    void testGetAllFoods() {
        // WHEN
        var result = foodService.getAllFoods();

        // THEN
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(foodRepository, times(1)).findAll();
    }

    @Test
    void testUpdateFood() {
        // GIVEN
        FoodDto updatedFoodDto = new FoodDto(FOOD_ID, "Updated Apple", 60, 0, 15, 1, null);
        when(foodRepository.save(any(Food.class))).thenReturn(food);

        // WHEN
        FoodDto result = foodService.updateFood(FOOD_ID, updatedFoodDto);

        // THEN
        assertNotNull(result);
        assertEquals("Updated Apple", result.getName());
        assertEquals(60, result.getCalorie());

        verify(foodRepository, times(1)).findById(FOOD_ID);
        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void testDeleteFood_FoodExists() {
        // WHEN
        foodService.deleteFood(FOOD_ID);

        // THEN
        verify(foodRepository, times(1)).findById(FOOD_ID);
        verify(foodRepository, times(1)).deleteById(FOOD_ID);
    }

    @Test
    void testDeleteFood_FoodNotFound() {
        // GIVEN
        when(foodRepository.findById(FOOD_ID)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> foodService.deleteFood(FOOD_ID));

        verify(foodRepository, times(1)).findById(FOOD_ID);
    }
}
