package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Vino;
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
  public ResponseEntity<ResponseVO> registrarVino(@RequestBody Vino vino) {
    ResultadoSP resultado = vinoService.registrarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Actualizar vinos
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarVino(@PathVariable Integer id, @RequestBody Vino vino) {
    vino.setIdVino(id);
    ResultadoSP resultado = vinoService.actualizarVino(vino);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Eliminar vino
  @PatchMapping("/{id}/eliminar")
  public ResponseEntity<ResponseVO> eliminarVino(@PathVariable Integer id) {
    ResultadoSP resultado = vinoService.eliminarVinoPorId(id);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Buscar vinos por nombre
  @GetMapping("/filtrar")
  public ResponseEntity<ResponseVO> filtrarVinoPorNombre(@RequestParam String nombre) {
    ResultadoSP resultado = vinoService.filtrarVinoPorNombre(nombre);

    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), null)
        : badRequest(resultado.getMensaje());
  }

  // Listar vinos
  @GetMapping
  public ResponseEntity<ResponseVO> listarVinos() {
    return ok(vinoService.listarVinos());
  }
}
