package com.gamm.vinos_api.dto.view;

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
  private String nombre;
  private Integer idCategoria;
  private String nombreCategoria;
  private String descripcionVino;
  private String estadoVino;
}
