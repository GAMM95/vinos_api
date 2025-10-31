package com.gamm.vinos_api.exception;

import com.gamm.vinos_api.dto.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseVO> manejarValidacion(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
    return ResponseEntity.badRequest().body(ResponseVO.error(mensaje));
  }
}