package com.example.onlinecourierservices.security;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ErrorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ApiResult<ApiResult.ErrorData> apiResult = ApiResult.errorResponse(authException.getMessage(),
                HttpServletResponse.SC_UNAUTHORIZED);
        String string = new ObjectMapper().writeValueAsString(apiResult);
        response.getWriter().write(string);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
