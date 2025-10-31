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

  private Integer codigoRespuesta;  // 1 = éxito, 0 o -1 = error
  private String mensaje;           // mensaje informativo o de error
  private Object data;              // resultado adicional (opcional)

  public ResultadoSP(Integer codigo, String mensaje) {
    this.codigoRespuesta = codigo;
    this.mensaje = mensaje;
  }

  /**
   * Helper: indica si el SP fue exitoso
   */
  public boolean esExitoso() {
    return codigoRespuesta != null && codigoRespuesta == 1;
  }
}