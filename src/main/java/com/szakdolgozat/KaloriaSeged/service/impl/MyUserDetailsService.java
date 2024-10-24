package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

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
