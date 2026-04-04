package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentasLitrosUserDTO {
  private Integer idUsuario;
  private BigDecimal litrosMesActual;
  private BigDecimal litrosTotales;
}
