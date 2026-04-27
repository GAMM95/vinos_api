package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComprobanteDTO {
  // Comprobante
  private Integer idComprobante;
  private String serie;
  private Integer numero;
  private String numeroComprobante;
  private String tipoComprobante;
  private LocalDateTime fechaEmision;
  private String estadoSunat;
  private String estado;

  // Venta
  private Integer idVenta;
  private String codVenta;
  private String metodoPago;
  private BigDecimal descuento;
  private BigDecimal total;
  private String observacion;

  // Sucursal
  private String nombreSucursal;
  private String direccionSucursal;

  // Vendedor
  private String vendedor;

  // Cliente
  private String clienteNombre;
  private String clienteCelular;
  private String clienteDireccion;

  // Detalle
  private String nombreVino;
  private BigDecimal cantidadLitros;
  private BigDecimal precioLitro;
  private BigDecimal subtotal;
}
