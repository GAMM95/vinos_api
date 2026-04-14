package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.response.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class DatabaseExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseVO> manejarViolacionIntegridad(DataIntegrityViolationException ex) {
    log.warn("Violación de integridad: {}", ex.getMessage());
    return build(HttpStatus.CONFLICT, "Datos duplicados o violación de integridad referencial.");
  }

  @ExceptionHandler(CannotGetJdbcConnectionException.class)
  public ResponseEntity<ResponseVO> manejarConexionDB(CannotGetJdbcConnectionException ex) {
    log.error("Sin conexión a BD: {}", ex.getMessage());
    return build(HttpStatus.SERVICE_UNAVAILABLE, "Servicio temporalmente no disponible.");
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ResponseVO> manejarSinResultados(EmptyResultDataAccessException ex) {
    return build(HttpStatus.NOT_FOUND, "No se encontraron datos.");
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ResponseVO> manejarSQL(SQLException ex) {
    log.error("Error SQL [{}]: {}", ex.getErrorCode(), ex.getMessage());
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos.");
  }

  private ResponseEntity<ResponseVO> build(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(ResponseVO.error(message));
  }
}
