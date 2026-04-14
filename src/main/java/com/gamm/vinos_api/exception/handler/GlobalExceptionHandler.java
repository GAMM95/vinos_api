package com.gamm.vinos_api.exception.handler;

import com.gamm.vinos_api.dto.response.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseVO> manejarValidacion(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
    return ResponseEntity.badRequest().body(ResponseVO.error(mensaje));
  }

  // Red de seguridad — SIEMPRE debe estar activa
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseVO> errorGeneral(Exception ex) {
    log.error("Error no controlado: ", ex); // stack trace completo al log
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseVO.error("Error interno del servidor."));
  }
}
