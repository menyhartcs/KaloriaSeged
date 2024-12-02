package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.dto.UserExerciseLogDto;
import com.szakdolgozat.KaloriaSeged.dto.UserFoodLogDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.entity.UserExerciseLog;
import com.szakdolgozat.KaloriaSeged.entity.UserFoodLog;
import com.szakdolgozat.KaloriaSeged.exception.ResourceNotFoundException;
import com.szakdolgozat.KaloriaSeged.repository.UserExerciseLogRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserFoodLogRepository;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private static final Long USER_ID = 2L;
    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_ROLE = "testrole";
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
    private static final List<UserExerciseLog> USER_EXERCISE_LOGS = new ArrayList<>();
    private static final List<UserFoodLogDto> USER_FOOD_LOG_DTOS = new ArrayList<>();
    private static final List<UserExerciseLogDto> USER_EXERCISE_LOG_DTOS = new ArrayList<>();

    private static final User USER = new User(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                    USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE,
            USER_FOOD_LOGS, USER_EXERCISE_LOGS);
    private static final UserDto USER_DTO = new UserDto(USER_ID, USER_NAME, USER_EMAIL, PASSWORD, GENDER, HEIGHT, WEIGHT, AGE,
                          USER_CALORIE, USER_PROTEIN, USER_CARBOHYDRATE, USER_FAT, USER_ROLE,
            USER_FOOD_LOG_DTOS, USER_EXERCISE_LOG_DTOS);

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFoodLogRepository userFoodLogRepository;
    @Mock
    private UserExerciseLogRepository userExerciseLogRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // GIVEN
        when(userRepository.save(any(User.class))).thenReturn(USER);

        // WHEN
        UserDto result = userService.createUser(USER_DTO);

        // THEN
        assertNotNull(result);
        assertEquals(USER.getName(), result.getName());
        assertEquals(USER.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserByIdFound() {
        // GIVEN
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(USER));

        // WHEN
        UserDto result = userService.getUserById(1L);

        // THEN
        assertNotNull(result);
        assertEquals(USER.getId(), result.getId());
    }

    @Test
    void testGetUserByIdNotFound() {
        // GIVEN
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByEmailFound() {
        // GIVEN
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(USER);

        // WHEN
        UserDto result = userService.getUserByEmail(USER_EMAIL);

        // THEN
        assertNotNull(result);
        assertEquals(USER.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserByEmailNotFound() {
        // GIVEN
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(null);

        // WHEN
        UserDto result = userService.getUserByEmail(USER_EMAIL);

        // THEN
        assertNull(result);
    }

    @Test
    void testGetAllUsers() {
        // GIVEN
        when(userRepository.findAll()).thenReturn(Arrays.asList(USER));

        // WHEN
        List<UserDto> result = userService.getAllUsers();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        // GIVEN
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(USER));
        when(userRepository.save(any(User.class))).thenReturn(USER);

        // WHEN
        UserDto result = userService.updateUser(1L, USER_DTO);

        // THEN
        assertNotNull(result);
        assertEquals(USER_DTO.getName(), result.getName());
        assertEquals(USER_DTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // GIVEN
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(USER));

        // WHEN
        userService.deleteUser(1L);

        // THEN
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        // GIVEN
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
