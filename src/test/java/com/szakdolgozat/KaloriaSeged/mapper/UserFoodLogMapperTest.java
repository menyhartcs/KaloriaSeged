package com.szakdolgozat.KaloriaSeged.mapper;

import com.szakdolgozat.KaloriaSeged.dto.FoodDto;
import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.Food;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFoodLogMapperTest {

    private static final Long USER_FOOD_LOG_ID = 1L;
    private static final Long FOOD_ID = 1L;
    private static final String FOOD_NAME = "Apple";
    private static final int CALORIE = 1;
    private static final int FAT = 1;
    private static final int CARBOHYDRATE = 1;
    private static final int PROTEIN = 1;
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
    private static final LocalDate DATE = LocalDate.of(2024, 11, 18);
    private static final int AMOUNT = 100;
    private static final User USER = new User(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOGS);
    private static final UserDto USER_DTO = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
            USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_FOOD_LOG_DTOS);
    private static final Food FOOD = new Food(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOGS);
    private static final FoodDto FOOD_DTO = new FoodDto(FOOD_ID, FOOD_NAME, CALORIE, FAT, CARBOHYDRATE, PROTEIN, USER_FOOD_LOG_DTOS);

    private UserFoodLog userFoodLog;
    private UserFoodLogDto userFoodLogDto;

    @BeforeEach
    void setUp() {
        userFoodLog = new UserFoodLog(USER_FOOD_LOG_ID, USER, FOOD, DATE, AMOUNT);
        userFoodLogDto = new UserFoodLogDto(USER_FOOD_LOG_ID, USER_DTO, FOOD_DTO, DATE, AMOUNT);
    }

    @Test
    void testMapToUserFoodLogDto() {
        // WHEN
        UserFoodLogDto result = UserFoodLogMapper.mapToUserFoodLogDto(userFoodLog);

        // THEN
        assertEquals(userFoodLog.getId(), result.getId());
        assertEquals(userFoodLog.getUser().getId(), result.getUser().getId());
        assertEquals(userFoodLog.getFood().getId(), result.getFood().getId());
        assertEquals(userFoodLog.getAmount(), result.getAmount());
        assertEquals(userFoodLog.getDate(), result.getDate());
    }

    @Test
    void testMapToUserFoodLog() {
        // WHEN
        UserFoodLog result = UserFoodLogMapper.mapToUserFoodLog(userFoodLogDto);

        // THEN
        assertEquals(userFoodLogDto.getId(), result.getId());
        assertEquals(userFoodLogDto.getUser().getId(), result.getUser().getId());
        assertEquals(userFoodLogDto.getFood().getId(), result.getFood().getId());
        assertEquals(userFoodLogDto.getAmount(), result.getAmount());
        assertEquals(userFoodLogDto.getDate(), result.getDate());
    }
}
