package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractRestController {

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

  // 204 No Content — sin body, como exige el estándar HTTP
  // ✅ Usar para acciones sin datos que retornar: marcarLeida, eliminar, etc.
  // ❌ NO usar con body — HTTP 204 prohíbe body en la respuesta
  protected ResponseEntity<Void> noContent() {
    return ResponseEntity.noContent().build();
  }
}

