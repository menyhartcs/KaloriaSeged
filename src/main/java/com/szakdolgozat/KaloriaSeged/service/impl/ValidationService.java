package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.exception.LoginException;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.util.LoginRequest;
import com.szakdolgozat.KaloriaSeged.util.ValidatePasswordUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The {@link ValidationService} handles the validation for the user login and registration.
 */
@AllArgsConstructor
@Service
public class ValidationService {

    private UserServiceImpl userServiceImpl;
    private ValidatePasswordUtil validatePasswordUtil;

    // Checks if the user credentials are correct for the login.
    public void loginUser(LoginRequest loginRequest) {
        UserDto user = userServiceImpl.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new LoginException("Az alábbi email cím nincs regisztrálva!");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new LoginException("Jelszó megadása kötelező!");
        }
        if (!validatePasswordUtil.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new LoginException("Hibás jelszó!");
        }
    }

    // Checks if the registration is valid.
    public void registerUser(UserDto user) {
        if (isValidSignUp(user)) {
            user.setPassword(validatePasswordUtil.encodePassword(user.getPassword()));
        }
    }

    // Proxy method, check if the signup is valid for clear understanding.
    public boolean isValidSignUp(UserDto user) {
        return isAllFieldsValid(user);
    }

    // Checks if all the form fields are valid.
    private boolean isAllFieldsValid(UserDto user) {
        if (user.getName().trim().isEmpty() && user.getEmail().trim().isEmpty() && user.getPassword().trim().isEmpty()) {
            throw new RegistrationException("Minden mező kitöltése kötelező!");
        }
        return isNameValid(user.getName()) && isEmailValid(user.getEmail()) && isPasswordValid(user.getPassword());
    }

    // Checks if name form field is valid.
    private boolean isNameValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RegistrationException("Név megadása kötelező!");
        }
        return true;
    }

    // Checks if email form field is valid.
    private boolean isEmailValid(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new RegistrationException("Email cím megadása kötelező!");
        }
        // Checks with REGEX if it is a valid email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new RegistrationException("Az email cím formátuma érvénytelen!");
        }
        if (userServiceImpl.getUserByEmail(email) != null) {
            throw new RegistrationException("Ez az email cím már regisztrálva van!");
        }
        return true;
    }

    // Checks if password form field is valid.
    private boolean isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Jelszó megadása kötelező!");
        }
        if (password.length() < 6) {
            throw new RegistrationException("A jelszónak legalább 6 karakter hosszúnak kell lennie!");
        }
        // Checks with REGEX if the password contains letters and numbers
        boolean hasLetter = password.matches(".*[a-zA-Z]+.*");
        boolean hasDigit = password.matches(".*[0-9]+.*");
        if (!hasLetter || !hasDigit) {
            throw new RegistrationException("A jelszónak tartalmaznia kell legalább egy betűt és egy számot!");
        }
        return true;
    }

}
