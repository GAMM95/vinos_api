package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistribucionDTO {
  private Integer idDistribucion;
  private Integer idAlmacen;
  private Integer idSucursal;
  private String sucursal;
  private String vino;
  private String origen;
  private String presentacion;
  private Integer cantidad;
  private LocalDateTime fechaDistribucion;
}
