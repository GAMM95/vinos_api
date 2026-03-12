package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalView {
  private Integer idSucursal;
  private String nombreSucursal;
  private String ubicacionSucursal;
  private Double latitudSucursal;
  private Double longitudSucursal;
  private String estadoSucursal;
}
