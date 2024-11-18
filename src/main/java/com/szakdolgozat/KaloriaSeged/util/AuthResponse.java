package com.szakdolgozat.KaloriaSeged.util;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class AuthResponse {

    private String token;
}
