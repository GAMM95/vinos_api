package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoSucursal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
  private Integer idSucursal;
  private String nombre;
  private String ubicacion;
  private Double latitud;
  private Double longitud;
  private EstadoSucursal estado;
}
