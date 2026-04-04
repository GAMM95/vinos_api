package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CantidadComprasMensualUserDTO {

  private Integer idUsuario;
  private String mes; // YYYY-MM-01
  private Integer cantidadCompras;
}