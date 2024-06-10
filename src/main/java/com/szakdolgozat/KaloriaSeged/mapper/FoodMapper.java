package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;

public class FoodMapper {

    public static FoodDto mapToFoodDto(Food food) {
        return new FoodDto(
                food.getId(),
                food.getName(),
                food.getCalorie(),
                food.getFat(),
                food.getCarbohydrate(),
                food.getProtein()
        );
    }

    public static Food mapToFood(FoodDto foodDto) {
        return new Food(
                foodDto.getId(),
                foodDto.getName(),
                foodDto.getCalorie(),
                foodDto.getFat(),
                foodDto.getCarbohydrate(),
                foodDto.getProtein()
        );
    }
}
