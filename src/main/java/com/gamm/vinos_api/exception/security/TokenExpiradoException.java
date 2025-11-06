package com.gamm.vinos_api.exception.security;

public class TokenExpiradoException extends RuntimeException {
  public TokenExpiradoException(String message) {
    super(message);
  }
}
