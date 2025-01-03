package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;

import java.util.List;

/**
 * Interface for the {@link FoodDto object CRUD operations.
 */
public interface FoodService {
    FoodDto createFood(FoodDto foodDto);
    FoodDto getFoodById(Long foodId);
    FoodDto getFoodByName(String name);
    List<FoodDto> getAllFoods();
    FoodDto updateFood(Long foodId, FoodDto updatedFood);
    void deleteFood(Long foodId);
}
