package com.gamm.vinos_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
  private Integer idNotificacion;
  private Integer idUsuarioDestino; // null = broadcast por rol
  private String rolDestino;        // "Administrador" o "Vendedor"
  private String tipo;              // SUCCESS, INFO, WARNING, ERROR
  private String titulo;
  private String mensaje;
  private String ruta;              // ruta Angular destino
  private Boolean leida;
  private LocalDateTime fechaCreacion;
}