package com.gamm.vinos_api.entities.dto.views;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VinoView {
  private Integer idVino;
  private String nombreVino;
  private String nombreCategoria;
  private Double precioVenta;
}
