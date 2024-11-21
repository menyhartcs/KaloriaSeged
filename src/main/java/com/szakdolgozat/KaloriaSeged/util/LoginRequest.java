package com.szakdolgozat.KaloriaSeged.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Util POJO class for login request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LoginRequest {

    private String email;
    private String password;
}
