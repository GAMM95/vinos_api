package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistribucionSucursal {
  private Integer idDistribucion;
  private Integer idAlmacen;
  private Integer idSucursal;
  private Integer cantidad;
  private LocalDateTime fecha;
  private String observacion;
}
