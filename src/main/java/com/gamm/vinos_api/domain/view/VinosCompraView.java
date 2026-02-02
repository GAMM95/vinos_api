package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VinosCompraView {
  private Integer idVino;
  private String nombreVino;
  private Integer idCatalogo;
  private Double precioUnidad;
  private Integer idProveedor;
  private String razonSocial;
  private Integer idCategoria;
  private String categoria;
  private Integer idPresentacion;
  private String presentacion;
  private String tipoVino;
  private String origenVino;
  private String estadoVino;
}
