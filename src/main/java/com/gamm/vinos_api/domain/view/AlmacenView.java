package com.gamm.vinos_api.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenView {
  private Integer idCatalogo;
  private Integer idVino;
  private String nombreVino;
  private String origen;
  private String presentacion;
  private Integer totalUnidades;
  private BigDecimal totalLitros;
}
