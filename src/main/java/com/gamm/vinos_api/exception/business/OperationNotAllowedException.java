package com.gamm.vinos_api.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OperationNotAllowedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public OperationNotAllowedException(String message) {
    super(message);
  }

  public OperationNotAllowedException(String message, Throwable cause) {
    super(message, cause);
  }
}