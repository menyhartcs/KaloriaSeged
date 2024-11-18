package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationControllerTest {
    private static final String EMAIL = "test@mail.com";
    private static final String NAME = "TestUser";
    private static final String PASSWORD = "testpw";
    private static final RegistrationRequest REGISTRATION_REQUEST = new RegistrationRequest(EMAIL, NAME, PASSWORD);

    @Mock
    private UserService userService;
    @Mock
    private ValidationService validationService;
    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSuccessfulRegistration() {
        // GIVEN

        // WHEN
        ResponseEntity<?> response = registrationController.register(REGISTRATION_REQUEST);

        // THEN
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sikeres regisztráció!", response.getBody());
        verify(validationService, times(1)).registerUser(any(UserDto.class));
        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void registerRegistrationValidationFails() throws RegistrationException {
        // GIVEN
        doThrow(new RegistrationException("Érvénytelen regisztrációs adatok!"))
                .when(validationService).registerUser(any(UserDto.class));

        // WHEN
        ResponseEntity<?> response = registrationController.register(REGISTRATION_REQUEST);

        // THEN
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Érvénytelen regisztrációs adatok!", response.getBody());
        verify(validationService, times(1)).registerUser(any(UserDto.class));
        verify(userService, never()).createUser(any(UserDto.class));
    }
}
