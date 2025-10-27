package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.entities.dto.ResponseVO;
import com.gamm.vinos_api.entities.dto.views.VinoView;
import com.gamm.vinos_api.entities.model.Vino;
import com.gamm.vinos_api.service.VinoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vinos")
@RequiredArgsConstructor
public class VinoController {
  private final VinoService vinoService;

  // Registrar vino
  @PostMapping
  public ResponseVO registrarVino(@RequestBody Vino vino) {
    ResultadoSP resultado = vinoService.registrarVino(vino);
    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), vino);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Actualizar vinos
  @PutMapping("/{id}")
  public ResponseVO actualizarVino(@PathVariable Integer id, @RequestBody Vino vino) {
    vino.setIdVino(id);
    ResultadoSP resultado = vinoService.actualizarVino(vino);
    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), vino);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Eliminar vino
  @PatchMapping("/{id}/eliminar")
  public ResponseVO eliminarVino(@PathVariable Integer id) {
    ResultadoSP resultado = vinoService.eliminarVinoPorId(id);

    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), null);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Buscar vinos por nombre
  @GetMapping("/filtrar")
  public ResponseVO filtrarVinoPorNombre(@RequestParam String nombre) {
    ResultadoSP resultado = vinoService.filtrarVinoPorNombre(nombre);

    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), resultado.getData());
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Listar vinos
  @GetMapping
  public ResponseVO listarVinos() {
    List<VinoView> vinos = vinoService.listarVinos();
    return ResponseVO.success(vinos);
  }
}
