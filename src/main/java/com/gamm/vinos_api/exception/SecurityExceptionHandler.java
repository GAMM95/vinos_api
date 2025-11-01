package com.gamm.vinos_api.exception;

import com.gamm.vinos_api.dto.ResponseVO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseVO> manejarAccesoDenegado(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ResponseVO.error("Acceso denegado: no tiene permisos para esta operación"));
  }


  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ResponseVO> manejarTokenExpirado(ExpiredJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("El token JWT ha expirado"));
  }

  @ExceptionHandler(io.jsonwebtoken.security.SecurityException.class)
  public ResponseEntity<ResponseVO> manejarFirmaJwt(io.jsonwebtoken.security.SecurityException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error("Token JWT inválido o manipulado"));
  }
}
