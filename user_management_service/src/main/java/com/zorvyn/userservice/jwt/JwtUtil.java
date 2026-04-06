package com.zorvyn.userservice.jwt;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private final JwtConfig jwtConfig;
	private Key signingKey;

	public JwtUtil(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
		this.signingKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	//  Generate token
	public String generateToken(String email, String role) {

		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtConfig.getExpirationMs());

		return Jwts.builder().setSubject(email) // main identity
				.claim("role", role) // custom claim
				.setIssuedAt(now).
				setExpiration(expiry).
				signWith(signingKey, SignatureAlgorithm.HS256).
				compact();
	}
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	                .setSigningKey(signingKey)
	                .build()
	                .parseClaimsJws(token);
	        return true;
	    } catch (JwtException | IllegalArgumentException ex) {
	    	 log.warn("Invalid JWT token: {}", ex.getMessage());
	            return false;
	        }
	    }
	
	

	private Claims getClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(signingKey)
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}

	// extract email : 
	public String extractEmail(String token) {
	    return getClaims(token).getSubject();
	}
// extract role : 
	public String extractRole(String token) {
	    return getClaims(token).get("role", String.class);
	}

	}


