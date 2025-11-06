package com.gamm.vinos_api.exception.security;

public class CredencialesInvalidas extends RuntimeException {
  public CredencialesInvalidas(String message) {
    super(message);
  }
}
