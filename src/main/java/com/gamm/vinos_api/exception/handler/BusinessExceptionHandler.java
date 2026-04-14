//package com.gamm.vinos_api.exception.handler;
//
//import com.gamm.vinos_api.dto.response.ResponseVO;
//import com.gamm.vinos_api.exception.business.EntityNotFoundException;
//import com.gamm.vinos_api.exception.business.OperationNotAllowedException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//
//public class BusinessExceptionHandler {
//  @ExceptionHandler(EntityNotFoundException.class)
//  public ResponseEntity<ResponseVO> noEncontrado(EntityNotFoundException ex) {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseVO.error(ex.getMessage()));
//  }
//
//  @ExceptionHandler(OperationNotAllowedException.class)
//  public ResponseEntity<ResponseVO> operacionNoPermitida(OperationNotAllowedException ex) {
//    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseVO.error(ex.getMessage()));
//  }
//}

package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.exception.business.EntityNotFoundException;
import com.gamm.vinos_api.exception.business.OperationNotAllowedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ResponseVO> manejarNegocio(BusinessException ex) {
    // Mensajes del SP — 400 porque el SP rechazó la operación por regla de negocio
    log.warn("Regla de negocio violada: {}", ex.getMessage());
    return ResponseEntity
        .badRequest()
        .body(ResponseVO.error(ex.getMessage()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseVO> noEncontrado(EntityNotFoundException ex) {
    // El mensaje lo define quien lanza la excepción — aquí solo se transmite y loguea
    log.warn("Recurso no encontrado: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ResponseVO.error(ex.getMessage()));
  }

  @ExceptionHandler(OperationNotAllowedException.class)
  public ResponseEntity<ResponseVO> operacionNoPermitida(OperationNotAllowedException ex) {
    log.warn("Operación no permitida: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ResponseVO.error(ex.getMessage()));
  }
}
