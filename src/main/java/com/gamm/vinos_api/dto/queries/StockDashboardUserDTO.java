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
public class StockDashboardUserDTO {
  private Integer idSucursal;
  private String nombreVino;
  private BigDecimal stockLitros;
  private BigDecimal totalLitros;
  private String estado;
}
