package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LitrosVendidosAdminDTO {
  private BigDecimal litrosMesActual;
  private BigDecimal litrosTotales;
}
