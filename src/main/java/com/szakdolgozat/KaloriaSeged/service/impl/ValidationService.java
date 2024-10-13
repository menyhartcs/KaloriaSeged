package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ValidationService {

    private UserServiceImpl userServiceImpl;

    public boolean isValidLogIn(String email, String rawPassword) {
        UserDto user = userServiceImpl.getUserByEmail(email);
        if (user != null) {
            return checkPassword(rawPassword, user.getPassword());
        }
        return false;
    }

    public void registerUser(UserDto user) {
        if (isValidSignUp(user)) {
            user.setPassword(encodePassword(user.getPassword()));
        }
    }

    public boolean isValidSignUp(UserDto user) {
        return isAllFieldsValid(user);
    }

    private boolean isAllFieldsValid(UserDto user) {
        if (user.getName().trim().isEmpty() && user.getEmail().trim().isEmpty() && user.getPassword().trim().isEmpty()) {
            throw new RegistrationException("Minden mező kitöltése kötelező!");
        }
        return isNameValid(user.getName()) && isEmailValid(user.getEmail()) && isPasswordValid(user.getPassword());
    }

    private boolean isNameValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RegistrationException("Név megadása kötelező!");
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new RegistrationException("Email cím megadása kötelező!");
        }
        if (userServiceImpl.getUserByEmail(email) != null) {
            throw new RegistrationException("Ez az email cím már regisztrálva van!");
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Jelszó megadása kötelező!");
        }
        return true;
    }

    private String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
