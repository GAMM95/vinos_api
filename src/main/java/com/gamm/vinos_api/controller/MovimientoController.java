package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.service.MovimientoService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Movimientos", description = "Consulta de movimientos de caja")
@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController extends AbstractRestController {

  private final MovimientoService movimientoService;

  // ─── Usuario autenticado ──────────────────────────────────────────────────

  @Operation(summary = "Listar mis movimientos paginados")
  @GetMapping("/mios")
  public ResponseEntity<ResponseVO> listarMisMovimientos(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(movimientoService.listarMisMovimientos(pagina, limite));
  }

  @Operation(summary = "Filtrar mis movimientos por rango de fechas")
  @GetMapping("/mios/filtro")
  public ResponseEntity<ResponseVO> filtrarMisMovimientosPorRango(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(
        movimientoService.filtrarMisMovimientosPorRango(fechaInicio, fechaFin, pagina, limite)
    );
  }

  @Operation(summary = "Ver detalle de movimientos de una caja (vendedor)")
  @GetMapping("/cajas/{idCaja}")
  public ResponseEntity<ResponseVO> detalleMovimientosUsuario(@PathVariable Integer idCaja) {
    return ResponseEntity.ok(movimientoService.listarDetalleMovimientoUsuario(idCaja));
  }

  @Operation(summary = "Filtrar movimientos por caja")
  @GetMapping("/cajas/filtro")
  public ResponseEntity<ResponseVO> filtrarPorCaja(@RequestParam Integer idCaja) {
    ResultadoSP resultado = movimientoService.filtrarPorCaja(idCaja);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), resultado.getData());
  }

  // ─── Administración ───────────────────────────────────────────────────────

  @Operation(summary = "Listar todos los movimientos paginados")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalMovimientos(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(movimientoService.listarTotalMovimientos(pagina, limite));
  }

  @Operation(summary = "Filtrar movimientos por usuario y/o rango de fechas")
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarMovimientosPorUsuarioORango(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return ResponseEntity.ok(movimientoService.filtrarMovimientosPorUsuarioORango(
        idUsuario, fechaInicio, fechaFin, pagina, limite
    ));
  }

  @Operation(summary = "Ver detalle de movimientos de una caja (administrador)")
  @GetMapping("/cajas/{idCaja}/admin")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> detalleMovimientosAdmin(@PathVariable Integer idCaja) {
    return ResponseEntity.ok(movimientoService.listarDetalleMovimientoAdmin(idCaja));
  }

}
