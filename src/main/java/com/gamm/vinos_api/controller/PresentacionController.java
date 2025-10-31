package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Presentacion;
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
  public ResponseEntity<ResponseVO> guardarPresentacion(@RequestBody Presentacion presentacion) {

    ResultadoSP resultado = presentacionService.guardarPresentacion(presentacion);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar presentacion
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarPresentacion(@PathVariable Integer id, @RequestBody Presentacion presentacion) {
    presentacion.setIdPresentacion(id);
    ResultadoSP resultado = presentacionService.actualizarPresentacion(presentacion);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Dar de baja a presentacion
  @PatchMapping("/{id}/darBaja")
  public ResponseEntity<ResponseVO> darBajaPresentacion(@PathVariable Integer id) {
    ResultadoSP resultado = presentacionService.darBaja(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Listar presentaciones
  @GetMapping
  public ResponseEntity<ResponseVO> listarPresentaciones() {
    return ok(presentacionService.listarPresentaciones());
  }
}
