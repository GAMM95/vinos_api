package com.gamm.vinos_api.service.impl;

import com.gamm.vinos_api.domain.model.Notificacion;
import com.gamm.vinos_api.dto.queries.NotificacionDTO;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.view.UsuarioView;
import com.gamm.vinos_api.repository.NotificacionRepository;
import com.gamm.vinos_api.repository.UsuarioRepository;
import com.gamm.vinos_api.security.util.SecurityUtils;
import com.gamm.vinos_api.service.NotificacionService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

  private final NotificacionRepository notificacionRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final UsuarioRepository usuarioRepository;

  public NotificacionServiceImpl(NotificacionRepository notificacionRepository,
                                 SimpMessagingTemplate messagingTemplate, UsuarioRepository usuarioRepository) {
    this.notificacionRepository = notificacionRepository;
    this.messagingTemplate = messagingTemplate;
    this.usuarioRepository = usuarioRepository;
  }

  // HELPERS PRIVADOS
  private Integer getUsuarioActual() {
    Integer idUsuario = SecurityUtils.getUserId();
    if (idUsuario == null) {
      throw new IllegalStateException("No hay usuario autenticado en el contexto de seguridad.");
    }
    return idUsuario;
  }

  private String getRolActual() {
    String rol = SecurityUtils.getRol();
    if (rol == null) {
      throw new IllegalStateException("No hay rol asignado al usuario autenticado.");
    }
    return rol;
  }

  private NotificacionDTO buildWsDto(Integer idNotificacion, String tipo, String titulo,  String mensaje, String ruta) {
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

  private Notificacion buildModelo(Integer idUsuarioDestino, String rolDestino, String tipo, String titulo, String mensaje, String ruta) {
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

  @Override
  public void notificarUsuario(Integer idUsuario, String username, String tipo, String titulo, String mensaje, String ruta) {
    // 1. Guardar y obtener la notif con ID generado
    Notificacion guardada = notificacionRepository.guardarNotificacion(
        buildModelo(idUsuario, null, tipo, titulo, mensaje, ruta)
    );

    // 2. Enviar por WS con el ID real
    messagingTemplate.convertAndSendToUser(
        username,
        "/queue/notificaciones",
        buildWsDto(guardada.getIdNotificacion(), tipo, titulo, mensaje, ruta)
    );
  }

  @Override
  public void notificarRol(String rol, String tipo, String titulo, String mensaje, String ruta) {
    Notificacion guardada = notificacionRepository.guardarNotificacion(
        buildModelo(null, rol, tipo, titulo, mensaje, ruta)
    );

    String topic = switch (rol) {
      case "Administrador" -> "/topic/notificaciones-admin";
      case "Vendedor"      -> "/topic/notificaciones-vendedor";
      default -> throw new IllegalArgumentException("Rol desconocido: " + rol);
    };

    messagingTemplate.convertAndSend(topic,
        buildWsDto(guardada.getIdNotificacion(), tipo, titulo, mensaje, ruta)
    );
  }

  @Override
  public void notificarRolYSucursal(String rol, Integer idSucursal, String tipo, String titulo, String mensaje, String ruta) {
    List<UsuarioView> usuarios =
        usuarioRepository.listarPorRolYSucursal(rol, idSucursal);

    for (UsuarioView u : usuarios) {
      notificarUsuario(
          u.getIdUsuario(),
          u.getUsername(),
          tipo,
          titulo,
          mensaje,
          ruta
      );
    }
  }

  @Override
  public ResponseVO obtenerMisNotificaciones() {
    List<NotificacionDTO> data = notificacionRepository
        .encontrarMisNotificaciones(getUsuarioActual(), getRolActual());
    return ResponseVO.success(data);
  }

  @Override
  public long contarNoLeidas() {
    return notificacionRepository.contarNoLeidas(getUsuarioActual(), getRolActual());
  }

  @Override
  public void marcarLeida(Integer idNotificacion) {
    Integer idUsuario = getUsuarioActual();
    notificacionRepository.marcarLeida(idNotificacion, idUsuario);
  }

  @Override
  public void marcarTodasLeidas() {
    notificacionRepository.marcarTodasLeidas(getUsuarioActual(), getRolActual());
  }
}