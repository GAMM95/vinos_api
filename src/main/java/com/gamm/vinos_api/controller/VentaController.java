package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "Ventas", description = "Operaciones relacionadas a ventas, detalle de ventas y carrito de ventas")
@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentaController extends AbstractRestController {

  private final VentaService ventaService;

  // ─── Carrito ──────────────────────────────────────────────────────────────

  @Operation(summary = "Listar carrito de venta del usuario autenticado")
  @GetMapping("/carrito/actual") // ✅ /carrito-user → /carrito/actual
  @SoloVendedor
  public ResponseEntity<ResponseVO> listarCarritoVentaUser() {
    return ok(ventaService.listarCarritoVentaUsuario());
  }

  @Operation(summary = "Listar carrito de venta de cualquier usuario")
  @GetMapping("/carrito")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarCarritoVenta(@RequestParam Integer idVenta) {
    return ok(ventaService.listarCarritoVentaAdmin(idVenta));
  }

  @Operation(summary = "Agregar producto al carrito de ventas")
  @PostMapping("/carrito")
  @SoloVendedor
  public ResponseEntity<ResponseVO> agregarProductoCarrito(
      @Valid @RequestBody Venta venta
  ) {
    ResultadoSP resultado = ventaService.agregarCarritoVenta(venta);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Retirar producto del carrito de ventas")
  @DeleteMapping("/carrito")
  @SoloVendedor
  public ResponseEntity<ResponseVO> retirarProductoCarrito(
      @RequestParam Integer idVenta,
      @RequestParam Integer idVino
  ) {
    ResultadoSP resultado = ventaService.retirarProductoCarrito(idVenta, idVino);
    return ok(resultado.getMensaje(), null);
  }

  // ─── Ventas ───────────────────────────────────────────────────────────────

  @Operation(summary = "Confirmar venta")
  @PostMapping("/confirmacion") // ✅ /confirmar → /confirmacion — sustantivo
  @SoloVendedor
  public ResponseEntity<ResponseVO> confirmarVenta(
      @RequestParam Integer idVenta,
      @RequestParam String metodoPago,
      @RequestParam BigDecimal descuento
  ) {
    ResultadoSP resultado = ventaService.confirmarVenta(idVenta, metodoPago, descuento);
    ResponseVO.validar(resultado);
    return ok(resultado.getMensaje(), null);
  }

  @Operation(summary = "Anular venta")
  @PatchMapping("/{idVenta}/anulacion") // ✅ POST /anular → PATCH /{id}/anulacion
  public ResponseEntity<ResponseVO> anularVenta(@PathVariable Integer idVenta) {
    ResultadoSP resultado = ventaService.anularVenta(idVenta);
    return ok(resultado.getMensaje(), null);
  }

  // ─── Consultas ────────────────────────────────────────────────────────────

  @Operation(summary = "Listar ventas del usuario autenticado")
  @GetMapping("/mias") // ✅ /ventas-user → /mias
  public ResponseEntity<ResponseVO> listarVentasUsuario(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(ventaService.listarVentasUsuario(pagina, limite));
  }

  @Operation(summary = "Listar todas las ventas")
  @GetMapping
  @SoloAdministrador
  public ResponseEntity<ResponseVO> listarTotalVentas(
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return okPaginado(ventaService.listarTotalVenta(pagina, limite));
  }

  @Operation(summary = "Ver detalle de una venta")
  @GetMapping("/{idVenta}/detalle") // ✅ /detalle-venta → /{id}/detalle
  public ResponseEntity<ResponseVO> listarDetalleVenta(@PathVariable Integer idVenta) {
    return ok(ventaService.listarDetalleVenta(idVenta));
  }

  @Operation(summary = "Filtrar mis ventas por rango de fechas")
  @GetMapping("/mias/filtro") // ✅ jerarquía clara
  @SoloVendedor
  public ResponseEntity<ResponseVO> filtrarMisVentas(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "5") int limite
  ) {
    return okPaginado(ventaService.filtrarMisVentasPorRango(fechaInicio, fechaFin, pagina, limite));
  }

  @Operation(summary = "Filtrar ventas por usuario y/o rango de fechas")
  @GetMapping("/filtro")
  @SoloAdministrador
  public ResponseEntity<ResponseVO> filtrarVentas(
      @RequestParam(required = false) Integer idUsuario,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
      @RequestParam(defaultValue = "1") int pagina,
      @RequestParam(defaultValue = "10") int limite
  ) {
    return okPaginado(ventaService.filtrarVentasPorUsuarioORango(idUsuario, fechaInicio, fechaFin, pagina, limite));
  }
}