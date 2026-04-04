package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoVentasUserDTO {
  private Integer idUsuario;
  private BigDecimal totalMesActual;
  private BigDecimal totalHistorico;
}
