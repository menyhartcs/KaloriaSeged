package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private static final Long USER_ID = 2L;
    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_NAME = "name";
    private static final String PASSWORD = "testpw";
    private static final String GENDER = "gender";
    private static final int HEIGHT = 180;
    private static final int WEIGHT = 75;
    private static final int AGE = 25;
    private static final int USER_CALORIE = 2000;
    private static final int USER_PROTEIN = 150;
    private static final int USER_CARBOHYDRATE = 300;
    private static final int USER_FAT = 60;
    private static final List<UserFoodLog> USER_FOOD_LOGS = new ArrayList<>();
    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOGS);
        userDto = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOG_DTOS);
    }

    @Test
    void testMapToUserDto() {
        // WHEN
        UserDto result = UserMapper.mapToUserDto(user);

        // THEN
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getGender(), result.getGender());
        assertEquals(user.getHeight(), result.getHeight());
        assertEquals(user.getWeight(), result.getWeight());
        assertEquals(user.getAge(), result.getAge());
        assertEquals(user.getCalorie(), result.getCalorie());
        assertEquals(user.getProtein(), result.getProtein());
        assertEquals(user.getCarbohydrate(), result.getCarbohydrate());
        assertEquals(user.getFat(), result.getFat());
        assertEquals(user.getFoodLogs().size(), result.getFoodLogs().size());
    }

    @Test
    void testMapToUser() {
        // WHEN
        User result = UserMapper.mapToUser(userDto);

        // THEN
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.getGender(), result.getGender());
        assertEquals(userDto.getHeight(), result.getHeight());
        assertEquals(userDto.getWeight(), result.getWeight());
        assertEquals(userDto.getAge(), result.getAge());
        assertEquals(userDto.getCalorie(), result.getCalorie());
        assertEquals(userDto.getProtein(), result.getProtein());
        assertEquals(userDto.getCarbohydrate(), result.getCarbohydrate());
        assertEquals(userDto.getFat(), result.getFat());
    }
}
