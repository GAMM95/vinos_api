package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraView {
  private Integer idCompra;
  private String codCompra;
  private LocalDate fecha;
  private String estado;

  private Integer idDetCompra;
  private String nombreVino;
  private String presentacion;
  private Double cantidadGalones;
  private Double precioUnidad;
  private Double subtotal;
}
