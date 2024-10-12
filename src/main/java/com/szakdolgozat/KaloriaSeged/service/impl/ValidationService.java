package com.szakdolgozat.KaloriaSeged.service.impl;

import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ValidationService {

    private UserRepository userRepository;

    public boolean isValidLogIn(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return checkPassword(rawPassword, user.getPassword());
        }
        return false;
    }

    public void registerUser(User user) {
        if (!isValidSignUp(user)) {
            throw new RegistrationException("Regisztrációs hiba.");
        }
        user.setPassword(encodePassword(user.getPassword()));
    }

    private boolean isValidSignUp(User user) {
        return user.getName() != null
                && user.getEmail() != null
                && user.getPassword() != null
                && userRepository.findByEmail(user.getEmail()) == null;
    }

    private String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
