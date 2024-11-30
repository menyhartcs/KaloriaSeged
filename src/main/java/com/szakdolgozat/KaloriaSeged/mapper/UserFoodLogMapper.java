package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

import java.time.LocalDate;

/**
 * Handles the mapping between {@link UserFoodLog} and {@link UserFoodLogDto} objects.
 */
public class UserFoodLogMapper {

    // Maps the UserFoodLog to UserFoodLogDto object.
    public static UserFoodLogDto mapToUserFoodLogDto(UserFoodLog userFoodLog) {
        UserFoodLogDto userFoodLogDto = new UserFoodLogDto();
        userFoodLogDto.setId(userFoodLog.getId());
        userFoodLogDto.setUser(createUserDto(userFoodLog));
        userFoodLogDto.setFood(createFoodDto(userFoodLog));
        userFoodLogDto.setAmount(userFoodLog.getAmount());
        if (userFoodLog.getDate() != null) {
            userFoodLogDto.setDate(userFoodLog.getDate());
        } else {
            userFoodLogDto.setDate(LocalDate.now());
        }
        return userFoodLogDto;
    }

    // INIT the FoodDto object for UserFoodLogDto object.
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

    // INIT the UserDto object for UserFoodLogDto object.
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
        userDto.setCalorie(userFoodLog.getUser().getCalorie());
        userDto.setProtein(userFoodLog.getUser().getProtein());
        userDto.setCarbohydrate(userFoodLog.getUser().getCarbohydrate());
        userDto.setFat(userFoodLog.getUser().getFat());
        userDto.setRole(userFoodLog.getUser().getRole());
        return userDto;
    }

    // Maps the UserFoodLogDto to UserFoodLog object.
    public static UserFoodLog mapToUserFoodLog(UserFoodLogDto userFoodLogDto) {
        UserFoodLog userFoodLog = new UserFoodLog();
        userFoodLog.setId(userFoodLogDto.getId());
        userFoodLog.setUser(UserMapper.mapToUser(userFoodLogDto.getUser()));
        userFoodLog.setFood(FoodMapper.mapToFood(userFoodLogDto.getFood()));
        userFoodLog.setAmount(userFoodLogDto.getAmount());
        if (userFoodLogDto.getDate() != null) {
            userFoodLog.setDate(userFoodLogDto.getDate());
        } else {
            userFoodLog.setDate(LocalDate.now());
        }
        return userFoodLog;
    }
}
