package com.gamm.vinos_api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamm.vinos_api.dto.response.ResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException {
    // Un request no autenticado - el token ya fue procesado por el filtro
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    // ObjectMapper serializa correctamente - sin concatenación de strings
    objectMapper.writeValue(
        response.getOutputStream(),
        ResponseVO.error("No autenticado. Inicie sesión para continuar.")
    );
  }
}

//  @Override
//  public void commence(HttpServletRequest request,
//                       HttpServletResponse response,
//                       AuthenticationException authException) throws IOException {
//    response.setContentType("application/json");
//    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//    String message;
//    if (authException instanceof BadCredentialsException) {
//      message = "Usuario o contraseña inválidos";
//    } else if (authException.getCause() instanceof ExpiredJwtException) {
//      message = "El token ha expirado";
//    } else {
//      message = "Acceso no autorizado";
//    }
//
//    response.getWriter().write("{\"success\": false, \"message\": \"" + message + "\"}");
//  }
