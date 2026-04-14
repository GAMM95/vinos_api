package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Caja;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.CajaService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Caja", description = "Gestión de apertura y cierre de cajas")
@RestController
@RequestMapping("/api/v1/cajas")
@RequiredArgsConstructor
public class CajaController extends AbstractRestController {

  private final CajaService cajaService;

  @Operation(summary = "Abrir caja")
  @PostMapping("/apertura") // ✅ sustantivo
  public ResponseEntity<ResponseVO> abrirCaja(@Valid @RequestBody Caja caja) {
    ResultadoSP resultado = cajaService.abrirCaja(caja);
    ResponseVO.validar(resultado);
    return created(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Cerrar caja")
  @PatchMapping("/{id}/cierre") // ✅ POST → PATCH + sustantivo
  public ResponseEntity<ResponseVO> cerrarCaja(@PathVariable Integer id) {
    ResultadoSP resultado = cajaService.cerrarCaja(id);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Obtener siguiente código de caja")
  @GetMapping("/siguiente-codigo")
  public ResponseEntity<ResponseVO> obtenerSiguienteCodigoCaja() {
    ResultadoSP resultado = cajaService.obtenerSiguienteCodigoCaja();
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  @Operation(summary = "Ver última caja abierta del usuario autenticado")
  @GetMapping("/mia/abierta") // ✅ /mi-ultima-caja-abierta → /mia/abierta
  public ResponseEntity<ResponseVO> mostrarMiUltimaCajaAbierta() {
    return ok(cajaService.mostrarMiUltimaCajaAbierta());
  }

  @Operation(summary = "Listar mis cajas paginadas")
  @GetMapping("/mias") // ✅ /mis-cajas → /mias
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarMisCajas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(cajaService.listarMisCajas(pagina, limite));
  }

  @Operation(summary = "Filtrar mis cajas por rango de fechas")
  @GetMapping("/mias/filtro") // ✅ jerarquía clara
  @SoloVendedor
  public ResponseEntity<ResponseVO> filtrarMisCajasPorRango(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(cajaService.filtrarMisCajasPorRango(fechaInicio, fechaFin, pagina, limite));
  }

  @Operation(summary = "Listar todas las cajas paginadas")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalCajas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(cajaService.listarTotalCajas(pagina, limite));
  }

  @Operation(summary = "Filtrar cajas por usuario y/o rango de fechas")
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarCajasPorUsuarioORango(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(cajaService.filtrarCajasPorUsuarioORango(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    ));
  }

}
