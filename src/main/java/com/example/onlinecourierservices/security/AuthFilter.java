package com.example.onlinecourierservices.security;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.service.AuthService;
import com.example.onlinecourierservices.utils.MessageConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            setUserPrincipal(request);
        } catch (Exception e) {
            System.out.println("error");
        }
        filterChain.doFilter(request, response);
    }

    private void setUserPrincipal(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            User user = getUserFromToken(authorization);
            if (user != null) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

    }

    private User getUserFromToken(String token) {
        token = token.substring(MessageConstants.TOKEN_TYPE.length());
        try {
            String emailFromToken = jwtTokenProvider.getEmailFromToken(token);
            Optional<User> user = authService.getUserByEmail(emailFromToken);
            return user.orElse(null);
        } catch (ExpiredJwtException e) {
            throw RestException.restThrow(e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }
}
