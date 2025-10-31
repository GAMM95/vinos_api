package com.gamm.vinos_api.exception;

import com.gamm.vinos_api.dto.ResponseVO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class DatabaseExceptionHandler {
  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ResponseVO> manejarSQL(SQLException ex) {
    String mensaje = "Error de base de datos: " + ex.getMessage();
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseVO.error(mensaje));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ResponseVO> manejarViolacionIntegridad(DataIntegrityViolationException ex) {
    String mensaje = "Violación de integridad referencial. Verifique relaciones o datos duplicados.";
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ResponseVO.error(mensaje));
  }
}
