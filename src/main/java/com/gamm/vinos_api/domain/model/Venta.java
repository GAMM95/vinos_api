package com.gamm.vinos_api.domain.model;

import com.gamm.vinos_api.domain.enums.EstadoVenta;
import com.gamm.vinos_api.domain.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Venta {
  private Integer idVenta;
  private String codVenta;
  private Sucursal sucursal;
  private LocalDateTime fecha;
  private MetodoPago metodoPago;
  private BigDecimal descuento;
  private BigDecimal total;
  private String observacion;
  private EstadoVenta estado = EstadoVenta.PENDIENTE;

  // Asociar el usuario que vende
  private Integer idUsuario;

  // Una venta puede tener 1 o varios detalles
  private List<DetalleVenta> detalles;
}