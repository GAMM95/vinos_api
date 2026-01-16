package com.gamm.vinos_api.domain.enums;

public enum TipoVino {
  A_GRANEL("A granel"),
  ENVASADO("Envasado");

  private final String valorBD;

  TipoVino(String valorBD){
    this.valorBD = valorBD;
  }

  public String getValorBD(){
    return valorBD;
  }

  public static TipoVino fromBD (String valor){
    for (TipoVino tipo : values()){
      if (tipo.valorBD.equalsIgnoreCase(valor)){
        return tipo;
      }
    }
    throw new IllegalArgumentException("Tipo de vino inválido: " + valor);
  }
}
