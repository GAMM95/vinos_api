package com.gamm.vinos_api.dto.view;

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
  private Integer idVino;
  private String nombreVino;
  private Integer idPresentacion;
  private String nombrePresentacion;
  private Double volumen;
  private String presentacion;
  private Double precioUnidad;
  private String tipoVino;
  private String estadoCatalogo;
}
