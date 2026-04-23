package com.gamm.vinos_api.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiradoException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public TokenExpiradoException(String message) {
    super(message);
  }

  public TokenExpiradoException(String message, Throwable cause) {
    super(message, cause);
  }
}
