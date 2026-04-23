package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Presentacion;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.PresentacionService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Presentaciones", description = "Gestión de presentaciones de vinos")
@RestController
@RequestMapping("/api/v1/presentaciones")
@RequiredArgsConstructor
@SoloAdministrador
public class PresentacionController extends AbstractRestController {
  private final PresentacionService presentacionService;

  @Operation(summary = "Registrar presentación")
  @PostMapping
  public ResponseEntity<ResponseVO> guardarPresentacion(@Valid @RequestBody Presentacion presentacion) {
    ResultadoSP resultado = presentacionService.guardarPresentacion(presentacion);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Actualizar presentación")
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> actualizarPresentacion(
      @PathVariable Integer id,
      @Valid @RequestBody Presentacion presentacion
  ) {
    presentacion.setIdPresentacion(id);
    ResultadoSP resultado = presentacionService.actualizarPresentacion(presentacion);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Cambiar estado de presentación")
  @PatchMapping("/{id}/estado")
  public ResponseEntity<ResponseVO> cambiarEstado(
      @PathVariable Integer id,
      @RequestParam boolean disponible
  ) {
    ResultadoSP resultado = disponible
        ? presentacionService.darAlta(id)
        : presentacionService.darBaja(id);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Filtrar presentaciones por descripción")
  @GetMapping("/filtro") // ✅ /filtrar → /filtro
  public ResponseEntity<ResponseVO> filtrarPresentaciones(@RequestParam String descripcion) {
    ResultadoSP resultado = presentacionService.filtrarPresentacion(descripcion);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Listar presentaciones")
  @GetMapping
  public ResponseEntity<ResponseVO> listarPresentaciones() {
    return ok(presentacionService.listarPresentaciones());
  }
}
