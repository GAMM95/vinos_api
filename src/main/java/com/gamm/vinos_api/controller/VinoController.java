package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Vino;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vinos")
@RequiredArgsConstructor
public class VinoController extends AbstractRestController {
  private final VinoService vinoService;

  // Registrar vino
  @PostMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> registrarVino(@RequestBody Vino vino) {
    ResultadoSP resultado = vinoService.registrarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar vinos
  @PutMapping("/{id}")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> actualizarVino(@PathVariable Integer id, @RequestBody Vino vino) {
    vino.setIdVino(id);
    ResultadoSP resultado = vinoService.actualizarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Eliminar vino
  @PatchMapping("/{id}/eliminar")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> eliminarVino(@PathVariable Integer id) {
    ResultadoSP resultado = vinoService.eliminarVinoPorId(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Buscar vinos por nombre
  @GetMapping("/filtrar")
  @Publico
  public ResponseEntity<ResponseVO> filtrarVinoPorNombre(@RequestParam String nombre) {
    ResultadoSP resultado = vinoService.filtrarVinoPorNombre(nombre);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

  // Listar vinos
  @GetMapping
  @Publico
  public ResponseEntity<ResponseVO> listarVinos() {
    return ok(vinoService.listarVinos());
  }
}
