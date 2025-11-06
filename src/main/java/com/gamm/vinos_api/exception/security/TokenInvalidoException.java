package com.gamm.vinos_api.exception.security;

public class TokenInvalidoException extends RuntimeException {
  public TokenInvalidoException(String message) {
    super(message);
  }
}
