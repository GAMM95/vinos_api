package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.response.ResponseVO;

public interface NotificacionService {

  // ─── Envío de notificaciones ──────────────────────────────────────────────

  // Notificar a un usuario específico
  void notificarUsuario(
      Integer idUsuario,
      String username,
      String tipo,
      String titulo,
      String mensaje,
      String ruta
  );

  // Notificar a todos los usuarios de un rol
  void notificarRol(
      String rol,
      String tipo,
      String titulo,
      String mensaje,
      String ruta
  );

  // Notificar a usuarios de un rol en una sucursal específica
  void notificarRolYSucursal(
      String rol,
      Integer idSucursal,
      String tipo,
      String titulo,
      String mensaje,
      String ruta
  );

  // ─── Consultas del usuario autenticado ────────────────────────────────────

  ResponseVO obtenerMisNotificaciones();

  long contarNoLeidas();

  void marcarLeida(Integer idNotificacion);

  void marcarTodasLeidas();
}
