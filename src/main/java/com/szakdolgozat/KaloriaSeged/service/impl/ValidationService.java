package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.exception.LoginException;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.util.LoginRequest;
import com.szakdolgozat.KaloriaSeged.util.ValidatePasswordUtil;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ValidationService {

    private UserServiceImpl userServiceImpl;
    private ValidatePasswordUtil validatePasswordUtil;

    public void loginUser(LoginRequest loginRequest) {
        UserDto user = userServiceImpl.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new LoginException("Hibás adatok!");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new RegistrationException("Jelszó megadása kötelező!");
        }
        if (!validatePasswordUtil.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new LoginException("Hibás jelszó!");
        }
    }

    public void registerUser(UserDto user) {
        if (isValidSignUp(user)) {
            user.setPassword(validatePasswordUtil.encodePassword(user.getPassword()));
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

}
