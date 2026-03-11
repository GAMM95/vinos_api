package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrecioView {
  private Integer idPrecio;
  private Integer idVino;
  private String nombreVino;
  private Integer idSucursal;
  private String nombreSucursal;
  private String origen;
  private BigDecimal precioVenta;

  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFin;
  private String estado;
}
