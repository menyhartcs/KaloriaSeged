package com.szakdolgozat.KaloriaSeged.util;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Util POJO class for the token authentication response.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class AuthResponse {

    private String token;
}
