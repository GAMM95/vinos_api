package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.exception.business.EntityNotFoundException;
import com.gamm.vinos_api.exception.business.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class BusinessExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseVO> noEncontrado(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseVO.error(ex.getMessage()));
  }

  @ExceptionHandler(OperationNotAllowedException.class)
  public ResponseEntity<ResponseVO> operacionNoPermitida(OperationNotAllowedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseVO.error(ex.getMessage()));
  }
}
