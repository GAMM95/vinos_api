package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoSucursal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
  private Integer idSucursal;
  private String nombre;
  private String ubicacion;
  private BigDecimal  latitud;
  private BigDecimal longitud;
  private EstadoSucursal estado;


}
