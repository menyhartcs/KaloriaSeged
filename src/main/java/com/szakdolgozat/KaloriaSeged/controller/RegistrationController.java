package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.exception.RegistrationException;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for registration. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private ValidationService validationService;

    // Handles the POST request for user registration.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        UserDto user = new UserDto();
        user.setEmail(registrationRequest.getEmail());
        user.setName(registrationRequest.getName());
        user.setPassword(registrationRequest.getPassword());
        user.setRole("ROLE_USER");

        try {
            validationService.registerUser(user);
        } catch (RegistrationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        userService.createUser(user);

        return ResponseEntity.ok("Sikeres regisztráció!");
    }


}


