package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.service.CombosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/combo")
public class CombosController extends AbstractRestController {
  private final CombosService combosService;

  // Llenar el combo de unidades de volumen
  @GetMapping("/unidad-volumen")
  public ResponseEntity<ResponseVO> comboUnidadVolumen() {
    return ok(combosService.comboUnidadVolumen());
  }

  // Llenar el combo de categorías
  @GetMapping("/categoria")
  public ResponseEntity<ResponseVO> comboCategoria() {
    return ok(combosService.comboCategoria());
  }
}
