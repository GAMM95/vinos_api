package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
  @Autowired
  private NotificacionService notificacionService;

  /** Obtener mis notificaciones - carga inicial del dropdown */
  @GetMapping
  public ResponseEntity<ResponseVO> obtenerNotificaciones() {
    return ResponseEntity.ok(notificacionService.obtenerMisNotificaciones());
  }

  /** Badge - cantidad de no leidas */
  @GetMapping("/no-leidas")
  public ResponseEntity<Long> contarNoLeidas() {
    return ResponseEntity.ok(notificacionService.contarNoLeidas());
  }

  /** Marcar una como leída al hacer click */
  @PatchMapping("/{idNotificacion}/leer")
  public ResponseEntity<Void> marcarLeida(
      @PathVariable Integer idNotificacion) {
    notificacionService.marcarLeida(idNotificacion);
    return ResponseEntity.ok().build();
  }

  /** Marcar todas como leídas */
  @PatchMapping("/leer-todas")
  public ResponseEntity<Void> marcarTodasLeidas() {
    notificacionService.marcarTodasLeidas();
    return ResponseEntity.ok().build();
  }
}
