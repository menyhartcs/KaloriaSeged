package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;

import java.util.List;

public class FoodMapper {

    public static FoodDto mapToFoodDto(Food food) {
        List<UserFoodLogDto> foodLogDtos = food.getFoodLogs().stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
        return new FoodDto(
                food.getId(),
                food.getName(),
                food.getCalorie(),
                food.getFat(),
                food.getCarbohydrate(),
                food.getProtein(),
                foodLogDtos
        );
    }

    public static Food mapToFood(FoodDto foodDto) {
        Food food = new Food();
        food.setId(foodDto.getId());
        food.setName(foodDto.getName());
        food.setCalorie(foodDto.getCalorie());
        food.setFat(foodDto.getFat());
        food.setCarbohydrate(foodDto.getCarbohydrate());
        food.setProtein(foodDto.getProtein());
        return food;
    }
}
