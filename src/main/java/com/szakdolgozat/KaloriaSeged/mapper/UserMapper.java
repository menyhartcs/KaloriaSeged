package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.User;

import java.util.List;

/**
 * Handles the mapping between {@link User} and {@link UserDto} objects.
 */
public class UserMapper {

    // Maps the User to UserDto object.
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
                user.getCalorie(),
                user.getProtein(),
                user.getCarbohydrate(),
                user.getFat(),
                foodLogDtos
        );
    }

    // Maps the UserDto to User object.
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
        user.setCalorie(userDto.getCalorie());
        user.setProtein(userDto.getProtein());
        user.setCarbohydrate(userDto.getCarbohydrate());
        user.setFat(userDto.getFat());
        return user;
    }
}
