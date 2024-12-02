package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();
    // INIT UserDto1
    private static final Long USER_ID = 1L;
    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_ROLE = "testrole";
    private static final String USER_NAME = "name";
    private static final String PASSWORD = "testpw";
    private static final String GENDER = "gender";
    private static final int HEIGHT = 180;
    private static final int WEIGHT = 75;
    private static final int AGE = 25;
    private static final int CALORIE = 2000;
    private static final int PROTEIN = 150;
    private static final int CARBOHYDRATE = 300;
    private static final int FAT = 60;
    // INIT UserDto2
    private static final Long USER_ID2 = 2L;
    private static final String USER_EMAIL2 = "test2@mail.com";
    private static final String USER_ROLE2 = "testrole2";
    private static final String USER_NAME2 = "name2";
    private static final String PASSWORD2 = "testpw2";
    private static final String GENDER2 = "gender2";
    private static final int HEIGHT2 = 160;
    private static final int WEIGHT2 = 55;
    private static final int AGE2 = 17;
    private static final int CALORIE2 = 1600;
    private static final int PROTEIN2 = 80;
    private static final int CARBOHYDRATE2 = 250;
    private static final int FAT2 = 50;
    // INIT UserDtos
    private static final UserDto USER_DTO1 =
            new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                    CALORIE, PROTEIN, CARBOHYDRATE, FAT,USER_ROLE, USER_FOOD_LOG_DTOS, USER_EXERCISE_LOG_DTOS);
    private static final UserDto USER_DTO2 =
            new UserDto(USER_ID2, USER_NAME2, USER_EMAIL2, PASSWORD2, GENDER2, HEIGHT2, WEIGHT2, AGE2,
                    CALORIE2, PROTEIN2, CARBOHYDRATE2, FAT2,USER_ROLE2, USER_FOOD_LOG_DTOS, USER_EXERCISE_LOG_DTOS);
    private static final List<UserDto> USERS = Arrays.asList(USER_DTO1, USER_DTO2);

    @Mock
    private UserService userService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // GIVEN
        doNothing().when(validationService).registerUser(USER_DTO1);
        when(userService.createUser(USER_DTO1)).thenReturn(USER_DTO1);

        // WHEN
        ResponseEntity<UserDto> response = userController.createUser(USER_DTO1);

        // THEN
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(USER_DTO1, response.getBody());
        verify(validationService, times(1)).registerUser(USER_DTO1);
        verify(userService, times(1)).createUser(USER_DTO1);
    }

    @Test
    void testGetUserById() {
        // GIVEN
        when(userService.getUserById(USER_ID)).thenReturn(USER_DTO1);

        // WHEN
        ResponseEntity<UserDto> response = userController.getUserById(USER_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_DTO1, response.getBody());
        verify(userService, times(1)).getUserById(USER_ID);
    }

    @Test
    void testGetUserByEmail() {
        // GIVEN
        when(userService.getUserByEmail(USER_EMAIL)).thenReturn(USER_DTO1);

        // WHEN
        ResponseEntity<UserDto> response = userController.getUserByEmail(USER_EMAIL);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_DTO1, response.getBody());
        verify(userService, times(1)).getUserByEmail(USER_EMAIL);
    }

    @Test
    void testGetAllUsers() {
        // GIVEN
        when(userService.getAllUsers()).thenReturn(USERS);

        // WHEN
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USERS, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testUpdateUser() {
        // GIVEN
        when(userService.updateUser(USER_ID, USER_DTO1)).thenReturn(USER_DTO1);

        // WHEN
        ResponseEntity<UserDto> response = userController.updateUser(USER_ID, USER_DTO1);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(USER_DTO1, response.getBody());
        verify(userService, times(1)).updateUser(USER_ID, USER_DTO1);
    }

    @Test
    void testDeleteUser() {
        // WHEN
        ResponseEntity<String> response = userController.deleteUser(USER_ID);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully", response.getBody());
        verify(userService, times(1)).deleteUser(USER_ID);
    }
}
