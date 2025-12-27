package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.PresentacionService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presentaciones")
@RequiredArgsConstructor
public class PresentacionController extends AbstractRestController {
  private final PresentacionService presentacionService;

  // Registrar presentacion
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> guardarPresentacion(@RequestBody Presentacion presentacion) {

    ResultadoSP resultado = presentacionService.guardarPresentacion(presentacion);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar presentacion
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarPresentacion(@PathVariable Integer id, @RequestBody Presentacion presentacion) {
    presentacion.setIdPresentacion(id);
    ResultadoSP resultado = presentacionService.actualizarPresentacion(presentacion);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Dar de baja/alta a presentación
  @PatchMapping("/{idPresentacion}/estado")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> cambiarEstadoPresentacion(
      @PathVariable Integer idPresentacion,
      @RequestParam("disponible") boolean disponible) {

    ResultadoSP resultado;
    if (disponible) {
      resultado = presentacionService.darAlta(idPresentacion);
    } else {
      resultado = presentacionService.darBaja(idPresentacion);
    }

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Filtrar usuarios
  @GetMapping("/filtrar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarPresentaciones(@RequestParam String descripcion  ) {
    ResultadoSP resultado = presentacionService.filtrarPresentacion(descripcion);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar presentaciones
  @GetMapping
  public ResponseEntity<ResponseVO> listarPresentaciones() {
    return ok(presentacionService.listarPresentaciones());
  }
}
