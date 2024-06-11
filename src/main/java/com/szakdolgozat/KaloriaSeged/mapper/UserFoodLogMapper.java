package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

public class UserFoodLogMapper {

    public static UserFoodLogDto mapToUserFoodLogDto(UserFoodLog userFoodLog) {
        return new UserFoodLogDto(
                userFoodLog.getId(),
                userFoodLog.getUser().getId(),
                userFoodLog.getFood().getId(),
                userFoodLog.getDate()
        );
    }

    public static UserFoodLog mapToUserFoodLog(UserFoodLogDto userFoodLogDto) {
        UserFoodLog userFoodLog = new UserFoodLog();
        userFoodLog.setId(userFoodLogDto.getId());

        User user = new User();
        user.setId(userFoodLogDto.getUserId());
        userFoodLog.setUser(user);

        Food food = new Food();
        food.setId(userFoodLogDto.getFoodId());
        userFoodLog.setFood(food);

        userFoodLog.setDate(userFoodLogDto.getDate());

        return userFoodLog;
    }
}
