package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadVolumen {
  private Integer idUnidadVolumen;
  private String unidad;
  private String abreviatura;
}
