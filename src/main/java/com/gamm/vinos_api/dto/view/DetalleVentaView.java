package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaView {
  private Integer idDetalleVenta;
  private Integer idVenta;
  private String nombreVino;
  private BigDecimal cantidadLitros;
  private BigDecimal precioLitro;
  private BigDecimal subtotal;
}
