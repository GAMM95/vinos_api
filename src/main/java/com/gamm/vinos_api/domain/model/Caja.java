package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoCaja;
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
public class Caja {
  private Integer idCaja;
  private String codCaja;
  private Sucursal sucursal;
  private Usuario usuario;
  private LocalDateTime fechaApertura;
  private LocalDateTime fechaCierre;
  private BigDecimal saldoInicial;
  private BigDecimal saldoActual;
  private BigDecimal saldoFinal;
  private EstadoCaja estadoCaja = EstadoCaja.ABIERTA;
}
