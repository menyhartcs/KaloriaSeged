package com.szakdolgozat.KaloriaSeged.exception;

/**
 * A type of {@link RuntimeException}, its thrown when something is wrong with the login.
 */
public class LoginException extends RuntimeException {

    public LoginException(String message) {
        super(message);
    }

}
