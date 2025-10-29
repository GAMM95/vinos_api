package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoView {
  private Integer idCatalogo;
  private Integer idProveedor;
  private String codProveedor;
  private String nombreProveedor;
  private String nombreVino;
  private String nombrePresentacion;
  private Double litrosUnidad;
  private Double precioUnidad;
}
