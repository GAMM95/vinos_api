package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.security.InvalidRefreshTokenException;
import com.gamm.vinos_api.exception.security.TokenExpiradoException;
import com.gamm.vinos_api.exception.security.TokenInvalidoException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

  //  Sí llega al advice — se lanza desde tu propio filtro/service JWT
  @ExceptionHandler(TokenExpiradoException.class)
  public ResponseEntity<ResponseVO> tokenExpirado(TokenExpiradoException ex) {
    log.warn("Token expirado: {}", ex.getMessage());
    return build(HttpStatus.UNAUTHORIZED, "El token ha expirado, vuelva a iniciar sesión.");
  }

  //  Sí llega — lanzada desde tu filtro o service
  @ExceptionHandler(TokenInvalidoException.class)
  public ResponseEntity<ResponseVO> tokenInvalido(TokenInvalidoException ex) {
    log.warn("Token inválido: {}", ex.getMessage());
    return build(HttpStatus.UNAUTHORIZED, "Token inválido o corrupto.");
  }

  //  Sí llega — lanzada desde tu endpoint de refresh
  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ResponseVO> refreshTokenInvalido(InvalidRefreshTokenException ex) {
    log.warn("Refresh token inválido: {}", ex.getMessage());
    return build(HttpStatus.UNAUTHORIZED, "Refresh token inválido o expirado.");
  }

  //  Sí llega — lanzada por AuthenticationManager en el endpoint de login
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseVO> credencialesInvalidas(BadCredentialsException ex) {
    log.warn("Credenciales inválidas en intento de login");
    return build(HttpStatus.UNAUTHORIZED, "Usuario o contraseña inválidos.");
  }

  //  Sí llega — lanzada por la librería JWT durante el parseo
  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<ResponseVO> tokenMalFormado(MalformedJwtException ex) {
    log.warn("JWT mal formado: {}", ex.getMessage());
    return build(HttpStatus.UNAUTHORIZED, "Token JWT mal formado.");
  }

  //  Sí llega — lanzada por la librería JWT durante validación de firma
  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<ResponseVO> firmaInvalida(SignatureException ex) {
    log.warn("Firma JWT inválida: {}", ex.getMessage());
    return build(HttpStatus.UNAUTHORIZED, "Token JWT inválido o manipulado.");
  }

  private ResponseEntity<ResponseVO> build(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(ResponseVO.error(message));
  }
}
