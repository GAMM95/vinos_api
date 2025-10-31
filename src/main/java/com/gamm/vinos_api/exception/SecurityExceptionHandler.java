//package com.gamm.vinos_api.exception;
//
//import com.gamm.vinos_api.dto.ResponseVO;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.SignatureException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class SecurityExceptionHandler {
//
//  @ExceptionHandler(AccessDeniedException.class)
//  public ResponseEntity<ResponseVO> manejarAccesoDenegado(AccessDeniedException ex) {
//    return ResponseEntity.status(HttpStatus.FORBIDDEN)
//        .body(ResponseVO.error("Acceso denegado: no tiene permisos para esta operación"));
//  }
//
//  @ExceptionHandler(SignatureException.class)
//  public ResponseEntity<ResponseVO> manejarFirmaJwt(SignatureException ex) {
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//        .body(ResponseVO.error("Token JWT inválido o manipulado"));
//  }
//
//  @ExceptionHandler(ExpiredJwtException.class)
//  public ResponseEntity<ResponseVO> manejarTokenExpirado(ExpiredJwtException ex) {
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//        .body(ResponseVO.error("El token JWT ha expirado"));
//  }
//}