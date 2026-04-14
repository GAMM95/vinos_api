package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notificaciones", description = "Gestión de notificaciones del usuario autenticado")
@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController extends AbstractRestController {

  private final NotificacionService notificacionService;

  @Operation(summary = "Obtener mis notificaciones")
  @GetMapping
  public ResponseEntity<ResponseVO> obtenerNotificaciones() {
    return ResponseEntity.ok(notificacionService.obtenerMisNotificaciones());
  }

  @Operation(summary = "Contar notificaciones no leídas")
  @GetMapping("/no-leidas/cantidad") // ✅ /no-leidas → /no-leidas/cantidad — más descriptivo
  public ResponseEntity<ResponseVO> contarNoLeidas() {
    return ok(notificacionService.contarNoLeidas()); // ✅ usa ResponseVO consistente
  }

  @Operation(summary = "Marcar notificación como leída")
  @PatchMapping("/{id}/lectura") // ✅ /leer → /lectura — sustantivo
  public ResponseEntity<Void> marcarLeida(@PathVariable Integer id) {
    notificacionService.marcarLeida(id);
    return noContent();
  }

  @Operation(summary = "Marcar todas las notificaciones como leídas")
  @PatchMapping("/lectura") // ✅ /leer-todas → /lectura
  public ResponseEntity<Void> marcarTodasLeidas() {
    notificacionService.marcarTodasLeidas();
    return noContent();
  }
}
