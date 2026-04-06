package com.zorvyn.userservice.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zorvyn.userservice.constant.Constant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(Constant.AUTHORIZATION);

        // 1️ Check header
        if (authHeader == null || !authHeader.startsWith(Constant.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️ Extract token
        String token = authHeader.substring(7); //removes "Bearer " 

        // 3️ Validate token
        if (!jwtUtil.validateToken(token)) { // validate token : signature + expiry check
            filterChain.doFilter(request, response);
            return;
        }

        // 4️ Extract user details
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);
        
         List<SimpleGrantedAuthority> authorities =
        List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // 5️ Create authentication object
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email,null,authorities);
//Collections.emptyList() : no authorities
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        											
        log.info("User role from JWT: " + role);
        log.info("Authorities: " + authorities);


        // 6️ Set authentication in context // security context holder : tells Spring “user is logged in”
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 7️ Continue request
        filterChain.doFilter(request, response);
    }
}
