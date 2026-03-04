package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.MetodoPago;
import com.gamm.vinos_api.domain.enums.TipoMovimiento;
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
public class Movimiento {
  private Integer idMovimiento;
  private Caja caja;
  private TipoMovimiento tipoMovimiento;
  private MetodoPago metodoPago;
  private String concepto;
  private BigDecimal monto;
  private LocalDateTime fecha;
  private Compra compra;
  private Venta venta;
}
