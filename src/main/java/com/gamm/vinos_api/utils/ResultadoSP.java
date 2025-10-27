package com.gamm.vinos_api.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoSP implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Integer codigoRespuesta;  // pRespuesta
  private String mensaje;           // pMensaje
  private Object data;

  public ResultadoSP(Integer codigo, String mensaje) {
    this.codigoRespuesta = codigo;
    this.mensaje = mensaje;
  }
}