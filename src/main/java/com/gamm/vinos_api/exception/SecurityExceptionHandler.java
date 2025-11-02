package com.gamm.vinos_api.exception;

import com.gamm.vinos_api.dto.ResponseVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

  // Login fallido: usuario o contraseña incorrectos
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseVO> manejarCredenciales(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Usuario o contraseña inválidos"));
  }

  // Access token expirado
  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ResponseVO> manejarTokenExpirado(ExpiredJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("El token ha expirado"));
  }

  // Refresh token expirado o inválido
  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ResponseVO> manejarRefreshToken(InvalidRefreshTokenException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Refresh token inválido o expirado"));
  }

  // Token mal formado
  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<ResponseVO> manejarTokenMalFormado(MalformedJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Token JWT mal formado"));
  }

  // Firma inválida o manipulación
  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<ResponseVO> manejarFirmaJwt(SignatureException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Token JWT inválido o manipulado"));
  }

  // Access denied (rol o permisos insuficientes)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseVO> manejarAccesoDenegado(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ResponseVO.error("Acceso denegado: no tiene permisos para esta operación"));
  }

  // Cualquier otra excepción de autenticación
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ResponseVO> manejarAutenticacion(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Acceso no autorizado"));
  }
}
