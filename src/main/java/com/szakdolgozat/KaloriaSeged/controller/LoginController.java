package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import com.szakdolgozat.KaloriaSeged.util.AuthResponse;
import com.szakdolgozat.KaloriaSeged.util.JWTUtil;
import com.szakdolgozat.KaloriaSeged.util.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for login. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
public class LoginController {

    private UserService userService;
    private ValidationService validationService;
    private JWTUtil jwtUtil;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserDto user = userService.getUserByEmail(loginRequest.getEmail());
        try {
            validationService.loginUser(loginRequest);
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}


