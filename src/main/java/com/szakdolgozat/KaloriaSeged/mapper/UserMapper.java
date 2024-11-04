package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.User;

import java.util.List;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        List<UserFoodLogDto> foodLogDtos = user.getFoodLogs().stream()
                .map(UserFoodLogMapper::mapToUserFoodLogDto)
                .toList();
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getHeight(),
                user.getWeight(),
                user.getAge(),
                foodLogDtos
        );
    }

    public static User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setHeight(userDto.getHeight());
        user.setWeight(userDto.getWeight());
        user.setAge(userDto.getAge());
        return user;
    }
}
