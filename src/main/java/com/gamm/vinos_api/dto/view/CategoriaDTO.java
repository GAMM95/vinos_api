package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
  private Integer idCategoria;
  private String nombreCategoria;
  private String descripcionCategoria;
  private String estado;
}
