package com.gamm.vinos_api.exception.business;

public class OperationNotAllowedException extends RuntimeException {
  public OperationNotAllowedException(String msg) {
    super(msg);
  }
}