package com.gamm.vinos_api.exception.security;

//public class TokenInvalidoException extends RuntimeException {
//  public TokenInvalidoException(String message) {
//    super(message);
//  }
//}

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenInvalidoException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public TokenInvalidoException(String message) {
    super(message);
  }

  public TokenInvalidoException(String message, Throwable cause) {
    super(message, cause);
  }
}