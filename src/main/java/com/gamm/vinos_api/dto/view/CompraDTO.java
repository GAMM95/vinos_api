package com.gamm.vinos_api.dto.view;

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
public class CompraDTO {
  private Integer idCompra;
  private String codCompra;
  private LocalDateTime fechaCompra;
  private LocalDateTime fechaCarrito;
  private String estado;
  private Integer idDetCompra;
  private String nombreVino;
  private String presentacion;
  private Integer cantidad;
  private BigDecimal subtotal;
  private BigDecimal subtotalCalculado;
  private BigDecimal costoTransporte;
  private BigDecimal costoEmbalaje;
  private BigDecimal costoEnvioAgencia;
  private BigDecimal costoLogistico;
  private BigDecimal totalCompra;
  private Integer idUsuario;
  private String usuario;
  private String username;
  private String rol;
}
