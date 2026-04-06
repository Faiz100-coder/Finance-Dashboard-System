package com.zorvyn.financeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.zorvyn.financeservice.jwt.JwtFilter;
import com.zorvyn.financeservice.security.CustomAccessDeniedHandler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

  

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	     http
    	    
            
            // 1️ Disabled CSRF (it not needed for REST APIs)
            .csrf(csrf -> csrf.disable())

            // 2️ Stateless session (JWT-based)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // handle exception wrong role and token 
           
            .exceptionHandling(ex -> ex
	    	        .authenticationEntryPoint(authenticationEntryPoint)
	    	        .accessDeniedHandler(accessDeniedHandler)
	    	    )
            // 3️ Authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/login/**",	
                    "/auth/users/**"
                    
                ).permitAll()
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/v3/api-docs/**"
                    ).permitAll()
                .anyRequest().authenticated()
            )

            // 4️ Add JWT filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            )

            // 5️ Default settings
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    
    @PostConstruct
    public void checkMethodSecurity() {
        log.info("METHOD SECURITY ENABLED");
    }

}
