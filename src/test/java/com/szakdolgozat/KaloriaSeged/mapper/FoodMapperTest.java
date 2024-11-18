package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodMapperTest {

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

    @BeforeEach
    void setUp() {
        food = new Food(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOGS);
        foodDto = new FoodDto(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOG_DTOS);
    }

    @Test
    void testMapToFoodDto() {
        // WHEN
        FoodDto result = FoodMapper.mapToFoodDto(food);

        // THEN
        assertEquals(food.getId(), result.getId());
        assertEquals(food.getName(), result.getName());
        assertEquals(food.getCalorie(), result.getCalorie());
        assertEquals(food.getFat(), result.getFat());
        assertEquals(food.getCarbohydrate(), result.getCarbohydrate());
        assertEquals(food.getProtein(), result.getProtein());
        assertEquals(food.getFoodLogs().size(), result.getFoodLogs().size());
    }

    @Test
    void testMapToFood() {
        // WHEN
        Food result = FoodMapper.mapToFood(foodDto);

        // THEN
        assertEquals(foodDto.getId(), result.getId());
        assertEquals(foodDto.getName(), result.getName());
        assertEquals(foodDto.getCalorie(), result.getCalorie());
        assertEquals(foodDto.getFat(), result.getFat());
        assertEquals(foodDto.getCarbohydrate(), result.getCarbohydrate());
        assertEquals(foodDto.getProtein(), result.getProtein());
    }
}
