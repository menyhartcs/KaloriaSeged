package com.szakdolgozat.KaloriaSeged.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RegistrationRequest {
    private String name;
    private String email;
    private String password;
}
