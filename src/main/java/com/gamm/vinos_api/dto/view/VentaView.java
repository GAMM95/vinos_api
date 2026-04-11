package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaView {
  private Integer idVenta;
  private String codVenta;
  private Integer idSucursal;
  private String sucursal;
  private LocalDate fechaVenta;
  private String metodoPago;
  private BigDecimal descuento;
  private BigDecimal total;
  private Integer idUsuario;
  private String usuario;
  private String username;
  private String estado;
}
