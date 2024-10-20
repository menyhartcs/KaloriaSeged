package com.szakdolgozat.KaloriaSeged.config;

import com.szakdolgozat.KaloriaSeged.util.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCrypt passwordEncoder() {
        return new BCrypt();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> {
            request.requestMatchers(
                    "/registration*",
                    "/login*").permitAll();
            request.anyRequest().authenticated();
        });

        http.formLogin(formLogin -> formLogin.loginPage("/login")
                .usernameParameter("email").permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login"));

        http.logout(logOut -> logOut.logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","Idea-2e8e7cee")
                .logoutSuccessUrl("/login"));


        return http.build();
    }
}
