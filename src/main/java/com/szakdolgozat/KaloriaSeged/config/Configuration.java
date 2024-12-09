package com.szakdolgozat.KaloriaSeged.config;

import com.szakdolgozat.KaloriaSeged.util.JwtAuthenticationFilter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Spring bean configuration class.
 */
@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    // Creates bean for BCrypt.
    @Bean
    public BCrypt passwordEncoder() {
        return new BCrypt();
    }

    // Creates bean for SecurityFilterChain and configures the cors, authorization and authentication filtering.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS settings.
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:3000"));  // Front-end URL
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }));

        // JWT authentication filter setting.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Setting authorization for http request.
        http.authorizeHttpRequests(request -> {
            request.requestMatchers(
                    "/registration/register*",
                    "/api/login*",
                    "/complete").permitAll();
            request.anyRequest().authenticated();
        });


        // Setting the form login permission.
//        http.formLogin(formLogin -> formLogin.loginPage("/login")
//                .usernameParameter("email").permitAll()
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/login"));

        // Setting the logout.
        http.logout(logOut -> logOut.logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","Idea-2e8e7cee", "jwt")
                .logoutSuccessUrl("/login"));


        return http.build();
    }
}
