package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.AuthResponse;
import com.szakdolgozat.KaloriaSeged.util.JWTUtil;
import com.szakdolgozat.KaloriaSeged.util.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private static final String EMAIL = "test@mail.com";
    private static final String PASSWORD = "testpw";
    private static final String TOKEN = "token";

    @Mock
    private UserService userService;
    @Mock
    private ValidationService validationService;
    @Mock
    private JWTUtil jwtUtil;
    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        UserDto userDto = initUserDto();

        when(userService.getUserByEmail(EMAIL)).thenReturn(userDto);
        doNothing().when(validationService).loginUser(loginRequest);
        when(jwtUtil.generateToken(userDto)).thenReturn(TOKEN);

        // WHEN
        ResponseEntity<?> response = loginController.login(loginRequest);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new AuthResponse(TOKEN), response.getBody());
        verify(userService, times(1)).getUserByEmail(EMAIL);
        verify(validationService, times(1)).loginUser(loginRequest);
        verify(jwtUtil, times(1)).generateToken(userDto);
    }

    @Test
    void testLoginInvalidCredentials() {
        // GIVEN
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        UserDto userDto = initUserDto();

        when(userService.getUserByEmail(EMAIL)).thenReturn(userDto);
        doThrow(new RuntimeException("Invalid credentials")).when(validationService).loginUser(loginRequest);

        // WHEN
        ResponseEntity<?> response = loginController.login(loginRequest);

        // THEN
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(userService, times(1)).getUserByEmail(EMAIL);
        verify(validationService, times(1)).loginUser(loginRequest);
        verify(jwtUtil, never()).generateToken(userDto);
    }

    private static UserDto initUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(EMAIL);
        return userDto;
    }
}
