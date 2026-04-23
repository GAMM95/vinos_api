package com.gamm.vinos_api.domain.enums;

public enum Rol {
  ADMINISTRADOR("Administrador"),
  VENDEDOR("Vendedor");

  private final String label;

  Rol(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
