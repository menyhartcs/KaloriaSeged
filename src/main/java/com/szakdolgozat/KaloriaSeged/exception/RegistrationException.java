package com.szakdolgozat.KaloriaSeged.exception;

/**
 * A type of {@link RuntimeException}, its thrown when something is wrong with the registration.
 */
public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }

}
