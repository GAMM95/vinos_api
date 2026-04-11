package com.gamm.vinos_api.repository;

import com.gamm.vinos_api.domain.model.Notificacion;
import com.gamm.vinos_api.dto.queries.NotificacionDTO;

import java.util.List;

public interface NotificacionRepository {
  Notificacion guardarNotificacion(Notificacion notificacion);

  List<NotificacionDTO> encontrarMisNotificaciones(Integer idUsuario, String rol);

  long contarNoLeidas(Integer idUsuario, String rol);

  void marcarLeida(Integer idNotificacion, Integer idUsuario);

  void marcarTodasLeidas(Integer idUsuario, String rol);
}
