package com.szakdolgozat.KaloriaSeged.controller;

import com.szakdolgozat.KaloriaSeged.dto.UserDto;
import com.szakdolgozat.KaloriaSeged.entity.User;
import com.szakdolgozat.KaloriaSeged.service.UserService;
import com.szakdolgozat.KaloriaSeged.service.impl.MyUserDetailsService;
import com.szakdolgozat.KaloriaSeged.service.impl.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for {@link User}. Handles the requests coming from the client.
 */
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ValidationService validationService;

    // Handles the POST request for create User.
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        validationService.registerUser(userDto);
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Handles the GET request for find User by id.
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    // Handles the GET request for find User by email.
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    // Handles the GET request for find all User.
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Handles the PUT request for update User.
//    @PutMapping("{id}")
//    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
//                                                      @RequestBody UserDto updatedUser) {
//        UserDto userDto = userService.updateUser(userId, updatedUser);
//        return ResponseEntity.ok(userDto);
//    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId,
                                                      @RequestBody UserDto updatedUser, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        String requestedUserEmail = userService.getUserById(userId).getEmail();
        if (!currentUserEmail.equals(requestedUserEmail) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nincs jogosultságod módosítani más felhasználó adatait");
        }
        UserDto userDto = userService.updateUser(userId, updatedUser);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Felhasználó nem található");
        }
        return ResponseEntity.ok(userDto);
    }

    // Handles the DELETE request for delete User.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
