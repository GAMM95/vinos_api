package com.gamm.vinos_api.dto.view;

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
public class MovimientosDTO {
  private Integer idMovimiento;
  private Integer idUsuario;
  private String usuario;
  private Integer idCaja;
  private String codCaja;
  private LocalDateTime fechaMovimiento;
  private String tipo;
  private String metodoPago;
  private BigDecimal monto;
  private String concepto;
}
