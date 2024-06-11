package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;

import java.util.List;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        List<UserFoodLogDto> userFoodLogDtos = user.getFoodLogs().stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                userFoodLogDtos
        );
    }

    public static User mapToUser(UserDto userDto) {
        List<UserFoodLog> userFoodLogs = userDto.getFoodLogDtos().stream()
                .map(UserFoodLogMapper::mapToUserFoodLog)
                .toList();
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userFoodLogs
        );
    }
}
