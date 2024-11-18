package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.service.FoodService;
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

public class FoodControllerTest {

    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final Long FOOD_ID = 1L;
    private static final String FOOD_NAME = "Apple";
    private static final int CALORIE = 1;
    private static final int FAT = 1;
    private static final int CARBOHYDRATE = 1;
    private static final int PROTEIN = 1;
    private static final Long FOOD_ID2 = 2L;
    private static final String FOOD_NAME2 = "Banana";
    private static final int CALORIE2 = 2;
    private static final int FAT2 = 2;
    private static final int CARBOHYDRATE2 = 2;
    private static final int PROTEIN2 = 2;

    private static final FoodDto FOOD_DTO1 =
            new FoodDto(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOG_DTOS);
    private static final FoodDto FOOD_DTO2 =
            new FoodDto(FOOD_ID2, FOOD_NAME2, CALORIE2, FAT2, CARBOHYDRATE2, PROTEIN2, USER_FOOD_LOG_DTOS);

    private static final List<FoodDto> FOODS = Arrays.asList(FOOD_DTO1, FOOD_DTO2);

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFood() {
        // GIVEN
        when(foodService.createFood(FOOD_DTO1)).thenReturn(FOOD_DTO1);

        // WHEN
        ResponseEntity<FoodDto> response = foodController.createFood(FOOD_DTO1);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(FOOD_DTO1, response.getBody());
        verify(foodService, times(1)).createFood(FOOD_DTO1);
    }

    @Test
    void testGetFoodById() {
        // GIVEN
        when(foodService.getFoodById(FOOD_ID)).thenReturn(FOOD_DTO1);

        // WHEN
        ResponseEntity<FoodDto> response = foodController.getFoodById(FOOD_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(FOOD_DTO1, response.getBody());
        verify(foodService, times(1)).getFoodById(FOOD_ID);
    }

    @Test
    void testGetFoodByName() {
        // GIVEN
        when(foodService.getFoodByName(FOOD_NAME)).thenReturn(FOOD_DTO1);

        // WHEN
        ResponseEntity<FoodDto> response = foodController.getFoodById(FOOD_NAME);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(FOOD_DTO1, response.getBody());
        verify(foodService, times(1)).getFoodByName(FOOD_NAME);
    }

    @Test
    void testGetAllFoods() {
        // GIVEN
        when(foodService.getAllFoods()).thenReturn(FOODS);

        // WHEN
        ResponseEntity<List<FoodDto>> response = foodController.getAllFoods();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(FOODS, response.getBody());
        verify(foodService, times(1)).getAllFoods();
    }

    @Test
    void testUpdateFood() {
        // GIVEN
        when(foodService.updateFood(FOOD_ID, FOOD_DTO1)).thenReturn(FOOD_DTO1);

        // WHEN
        ResponseEntity<FoodDto> response = foodController.updateFood(FOOD_ID, FOOD_DTO1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(FOOD_DTO1, response.getBody());
        verify(foodService, times(1)).updateFood(FOOD_ID, FOOD_DTO1);
    }

    @Test
    void testDeleteFood() {
        // WHEN
        ResponseEntity<String> response = foodController.deleteFood(FOOD_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Food deleted successfully", response.getBody());
        verify(foodService, times(1)).deleteFood(FOOD_ID);
    }
}
