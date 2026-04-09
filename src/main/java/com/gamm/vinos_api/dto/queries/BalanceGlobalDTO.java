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
public class BalanceGlobalDTO {
  private BigDecimal balanceNeto;
  private BigDecimal ingresos;
  private BigDecimal egresos;
  private BigDecimal reversos;
}
