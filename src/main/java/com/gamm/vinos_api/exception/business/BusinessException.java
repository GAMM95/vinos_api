package com.gamm.vinos_api.exception.business;

import java.io.Serial;

public class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  // 400 Bad Request por defecto — el SP rechazó la operación
  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
