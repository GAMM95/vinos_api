package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockView {
  private Integer idStock;
  private Integer idSucursal;
  private String sucursal;
  private Integer idVino;
  private String nombreVino;
  private String presentacion;
  private String origen;
  private BigDecimal stockGalones;
  private BigDecimal stockLitros;
  private BigDecimal totalLitros;
  private String estado;
}
