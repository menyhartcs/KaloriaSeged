package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The {@link MyUserDetailsService} class provides user data for the Spring security.
 * The class loads the user details by the email and provides a {@link UserDetails} object,
 * that the Spring Security uses for the user authentication.
 */
@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    // Loads the user details for the Spring security.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Felhasználó nem található: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
