package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompra {
  private Integer idDetalleCompra;
  private Integer idCompra;
  private Integer idCatalogo;
  private Integer cantidad;
  private BigDecimal subtotal;
}
