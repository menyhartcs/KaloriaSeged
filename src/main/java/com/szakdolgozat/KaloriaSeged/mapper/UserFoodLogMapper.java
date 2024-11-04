package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

import java.time.LocalDate;

public class UserFoodLogMapper {

    public static UserFoodLogDto mapToUserFoodLogDto(UserFoodLog userFoodLog) {
        UserFoodLogDto userFoodLogDto = new UserFoodLogDto();
        userFoodLogDto.setId(userFoodLog.getId());
        userFoodLogDto.setUser(createUserDto(userFoodLog));
        userFoodLogDto.setFood(createFoodDto(userFoodLog));
        if (userFoodLog.getDate() != null) {
            userFoodLogDto.setDate(userFoodLog.getDate());
        } else {
            userFoodLogDto.setDate(LocalDate.now());
        }
        return userFoodLogDto;
    }

    private static FoodDto createFoodDto(UserFoodLog userFoodLog) {
        FoodDto foodDto = new FoodDto();
        foodDto.setId(userFoodLog.getFood().getId());
        foodDto.setName(userFoodLog.getFood().getName());
        foodDto.setCalorie(userFoodLog.getFood().getCalorie());
        foodDto.setFat(userFoodLog.getFood().getFat());
        foodDto.setCarbohydrate(userFoodLog.getFood().getCarbohydrate());
        foodDto.setProtein(userFoodLog.getFood().getProtein());
        return foodDto;
    }

    private static UserDto createUserDto(UserFoodLog userFoodLog) {
        UserDto userDto = new UserDto();
        userDto.setId(userFoodLog.getUser().getId());
        userDto.setName(userFoodLog.getUser().getName());
        userDto.setEmail(userFoodLog.getUser().getEmail());
        userDto.setPassword(userFoodLog.getUser().getPassword());
        userDto.setGender(userFoodLog.getUser().getGender());
        userDto.setHeight(userFoodLog.getUser().getHeight());
        userDto.setWeight(userFoodLog.getUser().getWeight());
        userDto.setAge(userFoodLog.getUser().getAge());
        return userDto;
    }

    public static UserFoodLog mapToUserFoodLog(UserFoodLogDto userFoodLogDto) {
        UserFoodLog userFoodLog = new UserFoodLog();
        userFoodLog.setId(userFoodLogDto.getId());
        userFoodLog.setUser(UserMapper.mapToUser(userFoodLogDto.getUser()));
        userFoodLog.setFood(FoodMapper.mapToFood(userFoodLogDto.getFood()));
        if (userFoodLogDto.getDate() != null) {
            userFoodLog.setDate(userFoodLogDto.getDate());
        } else {
            userFoodLog.setDate(LocalDate.now());
        }
        return userFoodLog;
    }
}
