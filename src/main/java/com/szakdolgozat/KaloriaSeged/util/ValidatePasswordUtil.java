package com.szakdolgozat.KaloriaSeged.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Util class for password validation
 */
@Component
public class ValidatePasswordUtil {

    // Using BCrypt to hash the password
    public String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    // Using BCrypt to check if the passwords are identical
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
