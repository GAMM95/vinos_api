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
public class CarritoCompraDTO {
  private Integer idCompra;
  private String codCompra;
  private LocalDateTime fechaCarrito;
  private String estado;
  private Integer idDetCompra;
  private String nombreVino;
  private String presentacion;
  private Integer cantidad;
  private BigDecimal subtotal;
  private Integer idUsuario;
  private String usuario;
}
