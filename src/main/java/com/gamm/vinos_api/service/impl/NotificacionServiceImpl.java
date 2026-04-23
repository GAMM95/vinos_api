package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Notificacion;
import com.gamm.vinos_api.dto.queries.NotificacionDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.UsuarioDTO;
import com.gamm.vinos_api.repository.NotificacionRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.service.base.BaseService;
import com.gamm.vinos_api.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl extends BaseService implements NotificacionService {

  private final NotificacionRepository notificacionRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final UsuarioRepository usuarioRepository;

  // ─── Helpers privados ─────────────────────────────────────────────────────

  private NotificacionDTO buildWsDto(
      Integer idNotificacion, String tipo,
      String titulo, String mensaje, String ruta
  ) {
    NotificacionDTO dto = new NotificacionDTO();
    dto.setIdNotificacion(idNotificacion);
    dto.setTipo(tipo);
    dto.setTitulo(titulo);
    dto.setMensaje(mensaje);
    dto.setRuta(ruta);
    dto.setLeida(false);
    dto.setFechaCreacion(LocalDateTime.now());
    return dto;
  }

  private Notificacion buildModelo(
      Integer idUsuarioDestino, String rolDestino,
      String tipo, String titulo, String mensaje, String ruta
  ) {
    return Notificacion.builder()
        .idUsuarioDestino(idUsuarioDestino)
        .rolDestino(rolDestino)
        .tipo(tipo)
        .titulo(titulo)
        .mensaje(mensaje)
        .ruta(ruta)
        .leida(false)
        .build();
  }

  // ─── Notificaciones ───────────────────────────────────────────────────────

  @Override
  public void notificarUsuario(
      Integer idUsuario, String username,
      String tipo, String titulo, String mensaje, String ruta
  ) {
    Notificacion guardada = notificacionRepository.guardarNotificacion(
        buildModelo(idUsuario, null, tipo, titulo, mensaje, ruta)
    );
    messagingTemplate.convertAndSendToUser(
        username,
        "/queue/notificaciones",
        buildWsDto(guardada.getIdNotificacion(), tipo, titulo, mensaje, ruta)
    );
  }

  @Override
  public void notificarRol(
      String rol, String tipo,
      String titulo, String mensaje, String ruta
  ) {
    Notificacion guardada = notificacionRepository.guardarNotificacion(
        buildModelo(null, rol, tipo, titulo, mensaje, ruta)
    );

    // mapa explícito — más fácil de mantener que un switch con string magic
    String topic = switch (rol) {
      case "Administrador" -> "/topic/notificaciones-admin";
      case "Vendedor" -> "/topic/notificaciones-vendedor";
      default -> {
        log.warn("Intento de notificar rol desconocido: {}", rol); //
        throw new IllegalArgumentException("Rol desconocido: " + rol);
      }
    };

    messagingTemplate.convertAndSend(
        topic,
        buildWsDto(guardada.getIdNotificacion(), tipo, titulo, mensaje, ruta)
    );
  }

  @Override
  public void notificarRolYSucursal(
      String rol, Integer idSucursal,
      String tipo, String titulo, String mensaje, String ruta
  ) {
    List<UsuarioDTO> usuarios = usuarioRepository.listarPorRolYSucursal(rol, idSucursal);
    usuarios.forEach(u ->
        notificarUsuario(u.getIdUsuario(), u.getUsername(), tipo, titulo, mensaje, ruta)
    );
  }

  // ─── Consultas del usuario autenticado ────────────────────────────────────

  @Override
  public ResponseVO obtenerMisNotificaciones() {
    List<NotificacionDTO> data = notificacionRepository.encontrarMisNotificaciones(
        getIdUsuarioAutenticado(), getRolAutenticado()
    );
    return ResponseVO.success(data);
  }

  @Override
  public long contarNoLeidas() {
    return notificacionRepository.contarNoLeidas(
        getIdUsuarioAutenticado(), getRolAutenticado()
    );
  }

  @Override
  public void marcarLeida(Integer idNotificacion) {
    notificacionRepository.marcarLeida(idNotificacion, getIdUsuarioAutenticado());
  }

  @Override
  public void marcarTodasLeidas() {
    notificacionRepository.marcarTodasLeidas(
        getIdUsuarioAutenticado(), getRolAutenticado()
    );
  }
}