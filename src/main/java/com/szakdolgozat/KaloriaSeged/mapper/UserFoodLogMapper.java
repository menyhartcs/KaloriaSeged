package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

import java.time.LocalDate;

public class UserFoodLogMapper {

//    public static UserFoodLogDto mapToUserFoodLogDto(UserFoodLog userFoodLog) {
//        UserFoodLogDto userFoodLogDto = new UserFoodLogDto();
//        userFoodLogDto.setId(userFoodLog.getId());
//        userFoodLogDto.setUser(UserMapper.mapToUserDto(userFoodLog.getUser()));
//        userFoodLogDto.setFood(FoodMapper.mapToFoodDto(userFoodLog.getFood()));
//        if (userFoodLog.getDate() != null) {
//            userFoodLogDto.setDate(userFoodLog.getDate());
//        } else {
//            userFoodLogDto.setDate(LocalDate.now());
//        }
//        return userFoodLogDto;
//    }

    public static UserFoodLogDto mapToUserFoodLogDto(UserFoodLog userFoodLog) {
        UserFoodLogDto userFoodLogDto = new UserFoodLogDto();
        UserDto userDto = new UserDto();
        userDto.setId(userFoodLog.getUser().getId());
        FoodDto foodDto = new FoodDto();
        foodDto.setId(userFoodLog.getFood().getId());
        userFoodLogDto.setId(userFoodLog.getId());
        userFoodLogDto.setUser(userDto);
        userFoodLogDto.setFood(foodDto);
        if (userFoodLog.getDate() != null) {
            userFoodLogDto.setDate(userFoodLog.getDate());
        } else {
            userFoodLogDto.setDate(LocalDate.now());
        }
        return userFoodLogDto;
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
