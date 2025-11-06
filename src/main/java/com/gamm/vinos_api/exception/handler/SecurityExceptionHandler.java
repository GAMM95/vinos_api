package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.exception.security.InvalidRefreshTokenException;
import com.gamm.vinos_api.exception.security.TokenExpiradoException;
import com.gamm.vinos_api.exception.security.TokenInvalidoException;
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

  /// Excepciones de seguridad personalizadas
  @ExceptionHandler(TokenExpiradoException.class)
  public ResponseEntity<ResponseVO> tokenExpirado(TokenExpiradoException ex) {
    return build(HttpStatus.UNAUTHORIZED, "El token ha expirado, vuelva a iniciar sesión");
  }

  @ExceptionHandler(TokenInvalidoException.class)
  public ResponseEntity<ResponseVO> tokenInvalido(TokenInvalidoException ex) {
    return build(HttpStatus.UNAUTHORIZED, "Token inválido o corrupto");
  }

  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ResponseVO> refreshTokenInvalido(InvalidRefreshTokenException ex) {
    return build(HttpStatus.UNAUTHORIZED, "Refresh token inválido o expirado");
  }

  /// Excepciones de spring y jwt
  // Credenciales inválidas
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ResponseVO> credencialesInvalidas(BadCredentialsException ex) {
    return build(HttpStatus.UNAUTHORIZED, "Usuario o contraseña inválidos");
  }

  // Token mal formado
  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<ResponseVO> tokenMalFormado(MalformedJwtException ex) {
    return build(HttpStatus.UNAUTHORIZED, "Token JWT mal formado");
  }

  // Firma inválida o manipulación
  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<ResponseVO> firmaInválida(SignatureException ex) {
    return build(HttpStatus.UNAUTHORIZED, "Token JWT inválido o manipulado");
  }

  // Access denied (rol o permisos insuficientes)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseVO> accesoDenegado(AccessDeniedException ex) {
    return build(HttpStatus.FORBIDDEN, "Acceso denegado: permisos insuficientes");
  }

  // Cualquier otra excepción de autenticación
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ResponseVO> falloAutenticacion(AuthenticationException ex) {
    return build(HttpStatus.UNAUTHORIZED, "No autenticado");
  }

  ///  Helper
  private ResponseEntity<ResponseVO> build(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(ResponseVO.error(message));
  }
}
