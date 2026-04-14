//package com.gamm.vinos_api.exception.security;
//
//public class InvalidRefreshTokenException extends RuntimeException {
//  public InvalidRefreshTokenException(String message) {
//    super(message);
//  }
//}

package com.gamm.vinos_api.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidRefreshTokenException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidRefreshTokenException(String message) {
    super(message);
  }

  public InvalidRefreshTokenException(String message, Throwable cause) {
    super(message, cause);
  }
}