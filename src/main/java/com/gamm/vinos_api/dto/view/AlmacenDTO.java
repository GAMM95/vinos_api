package com.gamm.vinos_api.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenDTO {
  private Integer idAlmacen;
  private Integer idCatalogo;
  private Integer idVino;
  private String nombreVino;
  private String origen;
  private String presentacion;
  private Integer totalUnidades;
  private BigDecimal totalLitros;
  private LocalDate fechaDistribucion;
}
