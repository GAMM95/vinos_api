package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
  private Integer idStock;
  private Sucursal sucursal;
  private Vino vino;
  private Integer stockGalones;
  private BigDecimal stockLitros;
  private EstadoStock estado;
}
