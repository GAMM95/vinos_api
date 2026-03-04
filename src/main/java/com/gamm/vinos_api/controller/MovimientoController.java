package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.dto.ResponseVO;
import com.gamm.vinos_api.security.annotations.Publico;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.MovimientoService;
import com.gamm.vinos_api.utils.ResultadoSP;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController extends AbstractRestController {

  private final MovimientoService movimientoService;

  // Filtrar mis movimientos por rango de fechas
  @GetMapping("/mis-movimientos/filtrar")
  public ResponseEntity<ResponseVO> filtrarMisMovimientosPorRango(
      @RequestParam LocalDate fechaInicio,
      @RequestParam LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = movimientoService.filtrarMisMovimientosPorRango(fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Filtrar movimientos por usuario y/o rango de fechas
  @GetMapping("/filtrar")
  public ResponseEntity<ResponseVO> filtrarMovimientosPorUsuarioORango(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) LocalDate fechaInicio,
      @RequestParam(required = false) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = movimientoService.filtrarMovimientosPorUsuarioORango(idUsuario, fechaInicio, fechaFin, pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar mis movimientos paginado
  @GetMapping("/mis-movimientos")
  public ResponseEntity<ResponseVO> listarMisMovimientos(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = movimientoService.listarMisMovimientos(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar todas los movimientos paginado
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalMovimientos(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    ResponseVO response = movimientoService.listarTotalMovimientos(pagina, limite);
    return ResponseEntity.ok(response);
  }

  // Listar detalle de movimientos para un caja (vendedor)
  @GetMapping("/{idCaja}/mis-movimientos")
  public ResponseEntity<ResponseVO> detalleMovimientosUsuario(
      @PathVariable Integer idCaja
  ) {
    ResponseVO response = movimientoService.listarDetalleMovimientoUsuario(idCaja);
    return ResponseEntity.ok(response);
  }

  // Listar detalle de movimientos para un caja (admin)
  @GetMapping("/{idCaja}/movimientos")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> detalleMovimientosAdmin(
      @PathVariable Integer idCaja
  ) {
    ResponseVO response = movimientoService.listarDetalleMovimientoAdmin(idCaja);
    return ResponseEntity.ok(response);
  }


  @GetMapping("/filtrar-caja")
  public ResponseEntity<ResponseVO> filtrarPorCaja(@RequestParam Integer idCaja) {
    ResultadoSP resultado = movimientoService.filtrarPorCaja(idCaja);
    return resultado.esExitoso()
        ? ok(resultado.getMensaje(), resultado.getData())
        : badRequest(resultado.getMensaje());
  }

}
