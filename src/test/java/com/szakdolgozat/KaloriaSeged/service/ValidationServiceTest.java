package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.exception.LoginException;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.service.impl.UserServiceImpl;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.LoginRequest;
import com.szakdolgozat.KaloriaSeged.util.ValidatePasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationServiceTest {

    private static final String USER_EMAIL = "test@mail.com";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "testpw";
    private static final String VALID_PASSWORD = "password123";
    private static final String SHORT_PASSWORD = "p123";
    private static final String INVALID_PASSWORD_ONLY_LETTER = "asdasd";
    private static final String INVALID_PASSWORD_ONLY_NUMBER = "123456";
    private static final String EMPTY_STRING = "";
    private static final String INVALID_EMAIL = "test@mail";

    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private ValidatePasswordUtil validatePasswordUtil;

    @InjectMocks
    private ValidationService validationService;

    private UserDto userDto;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setEmail(USER_EMAIL);
        userDto.setName(USER_NAME);
        userDto.setPassword(USER_PASSWORD);

        loginRequest = new LoginRequest();
        loginRequest.setEmail(USER_EMAIL);
        loginRequest.setPassword(VALID_PASSWORD);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserSuccess() {
        // GIVEN
        when(userServiceImpl.getUserByEmail(USER_EMAIL)).thenReturn(userDto);
        when(validatePasswordUtil.checkPassword(loginRequest.getPassword(), userDto.getPassword())).thenReturn(true);

        // WHEN
        validationService.loginUser(loginRequest);

        // THEN
        verify(userServiceImpl, times(1)).getUserByEmail(USER_EMAIL);
    }

    @Test
    void testLoginUserUserNotFound() {
        // GIVEN
        when(userServiceImpl.getUserByEmail(USER_EMAIL)).thenReturn(null);

        // WHEN & THEN
        LoginException exception = assertThrows(LoginException.class, () -> validationService.loginUser(loginRequest));
        assertEquals("Hibás adatok!", exception.getMessage());
    }

    @Test
    void testLoginUserInvalidPassword() {
        // GIVEN
        when(userServiceImpl.getUserByEmail(USER_EMAIL)).thenReturn(userDto);

        // WHEN & THEN
        LoginException exception = assertThrows(LoginException.class, () -> validationService.loginUser(loginRequest));
        assertEquals("Hibás jelszó!", exception.getMessage());
    }

    @Test
    void testRegisterUserSuccess() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setPassword(VALID_PASSWORD);
        when(validatePasswordUtil.encodePassword(loginRequest.getPassword())).thenReturn(userDto.getPassword());

        // WHEN
        validationService.registerUser(userDto);

        // THEN
        assertNotNull(userDto.getPassword());
    }

    @Test
    void testRegisterUserEmptyFields() {
        // GIVEN
        userDto.setName(" ");
        userDto.setEmail(" ");
        userDto.setPassword(" ");

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Minden mező kitöltése kötelező!", exception.getMessage());
    }

    @Test
    void testRegisterUserInvalidName() {
        // GIVEN
        userDto.setName(EMPTY_STRING);  // Invalid name
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(VALID_PASSWORD);

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Név megadása kötelező!", exception.getMessage());
    }

    @Test
    void testRegisterUserEmptyEmail() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(EMPTY_STRING);  // Invalid email
        userDto.setPassword(VALID_PASSWORD);

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Email cím megadása kötelező!", exception.getMessage());
    }

    @Test
    void testRegisterUserInvalidEmail() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(INVALID_EMAIL);  // Invalid email
        userDto.setPassword(VALID_PASSWORD);

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Az email cím formátuma érvénytelen!", exception.getMessage());
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(VALID_PASSWORD);

        when(userServiceImpl.getUserByEmail(USER_EMAIL)).thenReturn(userDto);

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Ez az email cím már regisztrálva van!", exception.getMessage());
    }

    @Test
    void testRegisterUserEmptyPassword() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(EMPTY_STRING);  // Invalid password

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("Jelszó megadása kötelező!", exception.getMessage());
    }

    @Test
    void testRegisterUserWithTooShortPassword() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(SHORT_PASSWORD);  // Invalid password

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("A jelszónak legalább 6 karakter hosszúnak kell lennie!", exception.getMessage());
    }

    @Test
    void testRegisterUserWithInvalidOnlyLetterPassword() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(INVALID_PASSWORD_ONLY_LETTER);  // Invalid password

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("A jelszónak tartalmaznia kell legalább egy betűt és egy számot!", exception.getMessage());
    }

    @Test
    void testRegisterUserWithInvalidOnlyNumberPassword() {
        // GIVEN
        userDto.setName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(INVALID_PASSWORD_ONLY_NUMBER);  // Invalid password

        // WHEN & THEN
        RegistrationException exception = assertThrows(RegistrationException.class, () -> validationService.registerUser(userDto));
        assertEquals("A jelszónak tartalmaznia kell legalább egy betűt és egy számot!", exception.getMessage());
    }
}
