package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {
  private Integer idDetalleVenta;
  private Integer idVenta;
  private Integer idVino;
  private BigDecimal cantidadLitros;
  private BigDecimal precioLitro;
  private BigDecimal subtotal;
}
