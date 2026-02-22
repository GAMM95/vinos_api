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
public class Almacen {
  private Integer idAlmacen;
  private DetalleCompra detalleCompra;
  private LocalDate fecha;
  private Integer cantidadProductos;
  private BigDecimal cantidadLitros;
  private EstadoRegistro estado = EstadoRegistro.ACTIVO;
}
