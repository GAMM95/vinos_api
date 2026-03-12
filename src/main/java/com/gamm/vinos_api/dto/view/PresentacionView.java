package com.gamm.vinos_api.dto.view;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresentacionView {
  private Integer idPresentacion;
  private String descripcion;
  private Double volumen;
  private String volumenTexto;
  private Integer idUnidadVolumen;
  private String unidad;
  private String estado;
}
