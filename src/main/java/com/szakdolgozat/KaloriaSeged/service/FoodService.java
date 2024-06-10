package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;

import java.util.List;

public interface FoodService {
    FoodDto createFood(FoodDto foodDto);
    FoodDto getFoodById(Long foodId);
    List<FoodDto> getAllFoods();
    FoodDto updateFood(Long foodId, FoodDto updatedFood);
    void deleteFood(Long foodId);
}
