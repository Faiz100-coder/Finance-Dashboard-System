package com.zorvyn.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppConfig {
	
	@Bean
	ModelMapper modelmapper() {
		return new ModelMapper();
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/api/user/**").permitAll() // Public endpoints
//            .anyRequest().authenticated()                 // Secure everything else
//        );
//    return http.build();
//}
	
	
}
