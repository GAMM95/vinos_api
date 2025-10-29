package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.service.PresentacionService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presentaciones")
@RequiredArgsConstructor
public class PresentacionController {
  private final PresentacionService presentacionService;

  // Registrar presentacion
  @PostMapping
  public ResponseVO guardarPresentacion(@RequestBody Presentacion presentacion) {

    ResultadoSP resultado = presentacionService.guardarPresentacion(presentacion);
    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), presentacion);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Actualizar presentacion
  @PutMapping("/{id}")
  public ResponseVO actualizarPresentacion(@PathVariable Integer id, @RequestBody Presentacion presentacion) {
    presentacion.setIdPresentacion(id);
    ResultadoSP resultado = presentacionService.actualizarPresentacion(presentacion);
    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), presentacion);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Dar de baja a presentacion
  @PatchMapping("/{id}/darBaja")
  public ResponseVO darBajaPresentacion(@PathVariable Integer id) {
    ResultadoSP resultado = presentacionService.darBaja(id);

    if (resultado.getCodigoRespuesta() == 1) {
      return new ResponseVO(true, resultado.getMensaje(), null);
    } else {
      return ResponseVO.error(resultado.getMensaje());
    }
  }

  // Listar presentaciones
  @GetMapping
  public ResponseVO listarPresentaciones() {
    List<Presentacion> presentacion = presentacionService.listarPresentaciones();
    return ResponseVO.success(presentacion);
  }
}
