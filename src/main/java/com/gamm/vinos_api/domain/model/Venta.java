package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Venta {
  private Integer idVenta;
  private String codVenta;
  private Sucursal sucursal;
  private LocalDateTime fecha;
  private MetodoPago metodoPago;
  private BigDecimal total;
  private String observacion;
}
