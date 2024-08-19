package com.rental_management.security.security_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll() // OpenAPI docs
                        .requestMatchers("/swagger-ui.html").permitAll() // Swagger UI
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("api/owners/**").hasRole("ADMIN")
                        .requestMatchers("api/properties/**").hasRole("ADMIN")
                        .requestMatchers("api/promotions/**").hasRole("ADMIN")
                        .requestMatchers("api/message/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("api/cards/**").hasRole("USER")
                        .requestMatchers("api/reviews/**").hasRole("USER")
                        .requestMatchers("api/bookings/**").hasRole("USER")
                        .requestMatchers("api/users/**").hasRole("USER")
                        .anyRequest().
                        authenticated());

        http.addFilterBefore(authenticationFilter(),UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(){
        return new AuthenticationFilter(jwtUtil, customUserDetailsService);
    }
}
