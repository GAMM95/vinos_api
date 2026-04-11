package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.response.ResponseVO;

public interface NotificacionService {
  void notificarUsuario(Integer idUsuario, String username,
                        String tipo, String titulo,
                        String mensaje, String ruta);

  void notificarRol(String rol, String tipo,
                    String titulo, String mensaje, String ruta);

  void notificarRolYSucursal(String rol, Integer idSucursal, String tipo, String titulo, String mensaje, String ruta);
  ResponseVO obtenerMisNotificaciones();

  long contarNoLeidas();

  void marcarLeida(Integer idNotificacion);

  void marcarTodasLeidas();
}
