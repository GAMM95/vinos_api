package com.gamm.vinos_api.exception;

import com.gamm.vinos_api.dto.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /** ==============================
   * VALIDACIONES DE CAMPOS (Bean Validation)
   * ============================== */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseVO> manejarValidacion(MethodArgumentNotValidException exception) {
    String mensaje = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return ResponseEntity.badRequest().body(new ResponseVO(mensaje));
  }

  /** ==============================
   * CUALQUIER OTRA EXCEPCIÓN NO CONTROLADA
   * ============================== */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseVO> manejarExcepcionGeneral(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseVO.error("Error interno: " + ex.getMessage()));
  }



}
