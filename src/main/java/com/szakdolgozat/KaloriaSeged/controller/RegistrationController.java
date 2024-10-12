package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private ValidationService validationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        UserDto user = userService.getUserByEmail(registrationRequest.getEmail());
        if (!validationService.isValidSignUp(user)) {
            return ResponseEntity.badRequest().body("A felhasználónév már foglalt!");
        }

        // TODO re-think the registration logic placement
        userService.createUser(user);

        return ResponseEntity.ok("Sikeres regisztráció!");
    }


}


