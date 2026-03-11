package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoRegistro;
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
public class PrecioSucursal {
  private Integer idPrecio;
  private Vino vino;
  private Sucursal sucursal;
  private BigDecimal precioVenta;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
