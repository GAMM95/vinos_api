package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoPresentacion;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Presentacion {
  private Integer idPresentacion;
  private String descripcion;
  private Double volumen;
  private UnidadVolumen unidadVolumen;
  private EstadoPresentacion estado = EstadoPresentacion.DISPONIBLE;
}
