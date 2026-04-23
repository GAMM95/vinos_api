package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoVentaDTO {
  private Integer idDetalleVenta;
  private Integer idVenta;
  private Integer idVino;
  private String nombreVino;
  private String origen;
  private BigDecimal cantidadLitros;
  private BigDecimal precioLitro;
  private BigDecimal subTotal;
  private Integer idUsuario;
  private String usuario;
}
