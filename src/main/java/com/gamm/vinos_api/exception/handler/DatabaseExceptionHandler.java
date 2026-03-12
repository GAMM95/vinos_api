package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.response.ResponseVO;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class DatabaseExceptionHandler {

  // Tabla o constraint UNIQUE, FK etc.
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseVO> manejarViolacionIntegridad(DataIntegrityViolationException ex) {
    return build(HttpStatus.CONFLICT, "Violación de integridad referencial o datos duplicados: " + ex.getMessage());
  }

  // BD no disponible
  @ExceptionHandler(CannotGetJdbcConnectionException.class)
  public ResponseEntity<ResponseVO> manejarConexionDB(CannotGetJdbcConnectionException ex) {
    return build(HttpStatus.SERVICE_UNAVAILABLE, "No se pudo conectar a la base de datos: " + ex.getMessage());
  }

  // Consulta sin resultados cuando esperabas uno
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<ResponseVO> manejarSinResultados(EmptyResultDataAccessException ex) {
    return build(HttpStatus.NOT_FOUND, "No se encontraron datos: " + ex.getMessage());
  }

  // SQL puro
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ResponseVO> manejarSQL(SQLException ex) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la base de datos: " + ex.getMessage());
  }
  
  ///  Helper
  private ResponseEntity<ResponseVO> build(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(ResponseVO.error(message));
  }
}
