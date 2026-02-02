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

  // Llenar el combo de proveedores
  @GetMapping("/proveedor")
  public ResponseEntity<ResponseVO> comboProveedor() {
    return ok(combosService.comboProveedor());
  }

  // Llenar el combo de presentaciones
  @GetMapping("/presentacion")
  public ResponseEntity<ResponseVO> comboPresentacion() {
    return ok(combosService.comboPresentacion());
  }

  // Llenar el combo de vinos
  @GetMapping("/vino")
  public ResponseEntity<ResponseVO> comboVino() {
    return ok(combosService.comboVino());
  }

  // Llenar el combo de sucursales
  @GetMapping("/sucursal")
  public ResponseEntity<ResponseVO> comboSucursal() {
    return ok(combosService.comboSucursal());
  }

  // Llenar el checkbox de presentaciones
  @GetMapping("/chkPresentacion")
  public ResponseEntity<ResponseVO> checkBoxPresentacion() {
    return ok(combosService.checkBoxPresentacion());
  }
}
