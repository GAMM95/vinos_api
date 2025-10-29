package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VinoView {
  private Integer idVino;
  private String nombreVino;
  private String nombreCategoria;
  private Double precioVenta;
}
