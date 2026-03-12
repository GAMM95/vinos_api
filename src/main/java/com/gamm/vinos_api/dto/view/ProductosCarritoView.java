package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductosCarritoView {
  private Integer idDetCompra;
  private Integer idCompra;
  private String codCompra;
  private Integer idCatalogo;
  private String razonSocial;
  private String nombreVino;
  private String categoria;
  private String presentacion;
  private Double precioUnidad;
  private Integer cantidad;
  private Double subtotal;
}
