package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

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
        List<UserFoodLog> foodLogs = foodDto.getFoodLogDtos().stream()
                .map(UserFoodLogMapper::mapToUserFoodLog)
                .toList();
        return new Food(
                foodDto.getId(),
                foodDto.getName(),
                foodDto.getCalorie(),
                foodDto.getFat(),
                foodDto.getCarbohydrate(),
                foodDto.getProtein(),
                foodLogs
        );
    }
}
