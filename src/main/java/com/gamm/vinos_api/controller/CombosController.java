package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.service.CombosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Combos", description = "Datos para listas desplegables en formularios")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/combos")
public class CombosController extends AbstractRestController {
  private final CombosService combosService;

  @Operation(summary = "Combo de unidades de volumen")
  @GetMapping("/unidades-volumen")
  public ResponseEntity<ResponseVO> comboUnidadVolumen() {
    return ok(combosService.comboUnidadVolumen());
  }

  @Operation(summary = "Combo de categorías")
  @GetMapping("/categorias")
  public ResponseEntity<ResponseVO> comboCategoria() {
    return ok(combosService.comboCategoria());
  }

  @Operation(summary = "Combo de proveedores")
  @GetMapping("/proveedores")
  public ResponseEntity<ResponseVO> comboProveedor() {
    return ok(combosService.comboProveedor());
  }

  @Operation(summary = "Combo de presentaciones")
  @GetMapping("/presentaciones")
  public ResponseEntity<ResponseVO> comboPresentacion() {
    return ok(combosService.comboPresentacion());
  }

  @Operation(summary = "Combo de vinos")
  @GetMapping("/vinos")
  public ResponseEntity<ResponseVO> comboVino() {
    return ok(combosService.comboVino());
  }

  @Operation(summary = "Combo de sucursales")
  @GetMapping("/sucursales")
  public ResponseEntity<ResponseVO> comboSucursal() {
    return ok(combosService.comboSucursal());
  }

  @Operation(summary = "Checkbox de presentaciones")
  @GetMapping("/presentaciones/checkbox")  // ✅ chkPresentacion → sustantivo descriptivo
  public ResponseEntity<ResponseVO> checkBoxPresentacion() {
    return ok(combosService.checkBoxPresentacion());
  }

  @Operation(summary = "Combo de usuarios")
  @GetMapping("/usuarios")
  public ResponseEntity<ResponseVO> comboUsuario() {
    return ok(combosService.comboUsuario());
  }
}
