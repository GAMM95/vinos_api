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
public class CajaView {
  private Integer idCaja;
  private Integer idSucursal;
  private String sucursal;
  private Integer idUsuario;
  private String usuario;
  private LocalDateTime fechaApertura;
  private LocalDateTime fechaCierre;
  private BigDecimal saldoInicial;
  private BigDecimal saldoActual;
  private BigDecimal saldoFinal;
  private String estado;
}
