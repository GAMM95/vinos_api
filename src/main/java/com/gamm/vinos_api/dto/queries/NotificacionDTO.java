package com.gamm.vinos_api.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NotificacionDTO {
  private Integer idNotificacion;
  private String tipo;
  private String titulo;
  private String mensaje;
  private String ruta;
  private Boolean leida;
  private LocalDateTime fechaCreacion;
}
