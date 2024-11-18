package com.szakdolgozat.KaloriaSeged.service;

import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import com.szakdolgozat.KaloriaSeged.service.impl.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class MyUserDetailsServiceTest {

    private static final String EMAIL = "test@mail.com";
    private static final String PASSWORD = "testpw";

    private User user;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // GIVEN
        when(userRepository.findByEmail(EMAIL)).thenReturn(user);

        // WHEN
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(EMAIL);

        // THEN
        assertNotNull(userDetails);
        assertEquals(EMAIL, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        // GIVEN
        doThrow(new UsernameNotFoundException("Felhasználó nem található: ")).when(userRepository).findByEmail(EMAIL);

        // WHEN & THEN
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername(EMAIL));
    }
}
