package com.gamm.vinos_api.controller;

import com.gamm.vinos_api.domain.model.Venta;
import com.gamm.vinos_api.dto.response.ResponseVO;
import com.gamm.vinos_api.dto.request.ResultadoComprobante;
import com.gamm.vinos_api.security.annotations.SoloAdministrador;
import com.gamm.vinos_api.security.annotations.SoloVendedor;
import com.gamm.vinos_api.service.ComprobanteService;
import com.gamm.vinos_api.service.VentaService;
import com.gamm.vinos_api.util.ResultadoSP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
  private final ComprobanteService comprobanteService;

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

  @Operation(summary = "Confirmar venta — devuelve ticket para imprimir")
  @PostMapping("/confirmacion")
  @SoloVendedor
  public ResponseEntity<?> confirmarVenta(
      @RequestParam Integer idVenta,
      @RequestParam String metodoPago,
      @RequestParam BigDecimal descuento
  ) {
    // 1. Confirmar venta en BD (SP Tipo 5 → INSERT comprobante_venta)
    ResultadoSP resultado = ventaService.confirmarVenta(idVenta, metodoPago, descuento);
    ResponseVO.validar(resultado);  // lanza excepción si pRespuesta = 0

    // 2. Generar ambos PDFs + guardar A4 y ticket en disco
    ResultadoComprobante comprobante =
        comprobanteService.generarAmbos(idVenta);

    // 3. Devolver el TICKET al frontend para impresión inmediata
    String filename = "ticket-" + comprobante.numeroComprobante() + ".pdf";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + filename)
        .header("X-Numero-Comprobante", comprobante.numeroComprobante())
        .header("X-Id-Comprobante", comprobante.idComprobante().toString())
        .header("X-Mensaje", resultado.getMensaje())
        .contentType(MediaType.APPLICATION_PDF)
        .body(comprobante.pdfTicket());
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

  @GetMapping("/venta/{idVenta}/pdf-view")
  public ResponseEntity<byte[]> verComprobante(@PathVariable Integer idVenta) {
    byte[] pdf = comprobanteService.generarPdfPorVenta(idVenta);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=comprobante-" + idVenta + ".pdf") // 🔥 CLAVE
        .contentType(MediaType.APPLICATION_PDF)
        .body(pdf);
  }
}