package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractRestController {

  /**  Métodos para respuestas exitosas */

  // 200 OK - con datos
  protected ResponseEntity<ResponseVO> ok(Object data) {
    return ResponseEntity.ok(ResponseVO.success(data));
  }

  // 200 OK - con mensaje y datos
  protected ResponseEntity<ResponseVO> ok(String message, Object data) {
    return ResponseEntity.ok(ResponseVO.success(message, data));
  }

  // 201 Created - registro creado correctamente
  protected ResponseEntity<ResponseVO> created(String message, Object data) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResponseVO.success(message, data));
  }

  // 204 No Content - usado para actualizaciones o eliminaciones exitosas sin body
  protected ResponseEntity<ResponseVO> noContent(String message) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(ResponseVO.success(message, null));
  }

  /** Métodos para respuestas de error */

  // 400 Bad Request - error de validación o entrada inválida
  protected ResponseEntity<ResponseVO> badRequest(String message) {
    return ResponseEntity.badRequest()
        .body(ResponseVO.error(message));
  }

  // 401 Unauthorized - no autenticado
  protected ResponseEntity<ResponseVO> unauthorized(String message) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ResponseVO.error(message));
  }

  // 403 Forbidden - sin permisos
  protected ResponseEntity<ResponseVO> forbidden(String message) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ResponseVO.error(message));
  }

  // 404 Not Found - recurso no encontrado
  protected ResponseEntity<ResponseVO> notFound(String message) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ResponseVO.error(message));
  }

  // 409 Conflict - usado en duplicados o violaciones de integridad
  protected ResponseEntity<ResponseVO> conflict(String message) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ResponseVO.error(message));
  }

  // 500 Internal Server Error - error general
  protected ResponseEntity<ResponseVO> error(String message) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseVO.error(message));
  }

  // 500 Internal Server Error - con detalles de excepción
  protected ResponseEntity<ResponseVO> error(String message, Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseVO.error(message, e.getMessage()));
  }
}

